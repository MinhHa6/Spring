package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.service.category.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryController {
     CategoryService categoryService;
    /**
     * Trang ds san pham
     */
    @GetMapping
    public String listCategories(Model model,@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int size,
                                 @RequestParam(required = false) String keyword)
    {
        model.addAttribute("categories",categoryService.getAll());
        return "admin/category-list";// tro den file category-list.html
    }
    /**
     * Form tao moi category
     */
    @GetMapping("/create")
    public String createCategoryForm(Model model)
    {
        model.addAttribute("category",new Category());
        model.addAttribute("categories",categoryService.getAll());// de chon parent
        return "admin/category-form";
    }
    /**
     * Luu category moi hoac cap nhat category da co
     */
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category,
                               @RequestParam("iconFile") MultipartFile iconFile,
                               RedirectAttributes redirectAttributes) {
        try {
            if (!iconFile.isEmpty()) {
                //  Đường dẫn chính xác
                String uploadDir = "src/main/resources/static/uploads/";
                File directory = new File(uploadDir);

                if (!directory.exists()) {
                    directory.mkdirs();
                    System.out.println("Created directory: " + uploadDir);
                }

                String fileName = System.currentTimeMillis() + "_" + iconFile.getOriginalFilename();
                Path path = Paths.get(uploadDir, fileName);
                Files.write(path, iconFile.getBytes());

                System.out.println("File saved: " + path.toAbsolutePath()); // Log để check

                // Xóa ảnh cũ
                if (category.getId() != null) {
                    Category existing = categoryService.getCategoryId(category.getId());
                    if (existing != null && existing.getIcon() != null) {
                        File oldFile = new File(uploadDir + existing.getIcon());
                        if (oldFile.exists()) {
                            oldFile.delete();
                            System.out.println("Deleted old file: " + existing.getIcon());
                        }
                    }
                }

                category.setIcon(fileName);

            } else if (category.getId() != null) {
                Category existing = categoryService.getCategoryId(category.getId());
                if (existing != null) {
                    category.setIcon(existing.getIcon());
                    System.out.println("Keep old icon: " + existing.getIcon());
                }
            }

            categoryService.saveCategory(category);
            System.out.println("Category saved with icon: " + category.getIcon());

            redirectAttributes.addFlashAttribute("success", "Lưu thành công!");

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi upload: " + e.getMessage());
        }

        return "redirect:/category";
    }
    /**
     * Form chinh sua category theo id
     */
    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id")Long id,Model model)
    {
        Category category=categoryService.getCategoryId(id);
        if(category== null)
        {
            return "redirect:/category";
        }
        model.addAttribute("category",category);
        model.addAttribute("categories",categoryService.getAll());// de chon parent
        return "admin/category-form";
    }
    /**
     * Xoa category theo id
     */
    @GetMapping("delete/{id}")
    public String deleteCategory(@PathVariable("id")Long id)
    {
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }

    /**
     * Tim kiem category
     */
    @GetMapping("/search")
    public String searchCategory(Model model,@RequestParam("keyword")String keyword)
    {
        List<Category> categories=categoryService.searchByName(keyword);
        model.addAttribute("categories",categories);
        model.addAttribute("keyword",keyword);
        return "admin/category-list";
    }
}
