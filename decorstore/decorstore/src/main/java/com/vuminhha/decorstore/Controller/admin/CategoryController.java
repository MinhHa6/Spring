package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String saveCategory (@ModelAttribute("category")Category category)
    {
        categoryService.saveCategory(category);
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

}
