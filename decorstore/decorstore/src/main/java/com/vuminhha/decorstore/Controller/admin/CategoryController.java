package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController (CategoryService categoryService)
    {
        this.categoryService=categoryService;
    }
    /**
     * Trang ds san pham
     */
    @GetMapping
    public String listCategories(Model model)
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
                               @RequestParam("iconFile") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String uploadDir = new File("src/main/resources/static/uploads/").getAbsolutePath();
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir, fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());

                // Lưu tên file mới vào DB
                category.setIcon(fileName);
            } else {
                // Giữ lại icon cũ nếu không upload mới
                Category existing = categoryService.getCategoryId(category.getId());
                if (existing != null) {
                    category.setIcon(existing.getIcon());
                }
            }

            categoryService.saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
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
