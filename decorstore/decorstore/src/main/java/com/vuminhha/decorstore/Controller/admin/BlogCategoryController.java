package com.vuminhha.decorstore.controller.admin;

import com.vuminhha.decorstore.entity.BlogCategory;
import com.vuminhha.decorstore.service.news.PostCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog-categories")
public class BlogCategoryController {

    private final PostCategoryService categoryService;

    public BlogCategoryController(PostCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Hiển thị danh sách danh mục
    @GetMapping
    public String listCategories(Model model) {
        List<BlogCategory> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return "admin/blog-category-list"; // Template Thymeleaf
    }

    // Form tạo danh mục mới
    @GetMapping("/create")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new BlogCategory());
        return "admin/blog-category-form";
    }

    // Xử lý tạo danh mục
    @PostMapping("/save")
    public String createCategory(@ModelAttribute BlogCategory category, Model model) {
        categoryService.create(category);
        return "redirect:/blog-categories";
    }

    // Form chỉnh sửa danh mục
    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        BlogCategory category = categoryService.getById(id);
        if(category==null)
        {
            return "redirect:/blog-categories";
        }
        model.addAttribute("category", category);
        return "admin/blog-category-form";
    }


    // Xóa danh mục
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/blog-categories";
    }
}
