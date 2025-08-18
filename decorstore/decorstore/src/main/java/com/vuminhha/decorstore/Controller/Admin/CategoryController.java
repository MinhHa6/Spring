package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String listCategories(Model model)
    {
        model.addAttribute("categories",
        categoryService.getAll());
        return "admin/category-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("category",new Category());
        return "admin/category-form";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable ("id")Long id,Model model)
    {
        model.addAttribute("category",categoryService.getCategoryId(id));
        return "index/category-form";
    }
    @PostMapping("/create")
    public String saveCategory(@ModelAttribute ("category")Category category )
    {
        categoryService.saveCategory(category);
        return "redirect:/category";
    }
    @PostMapping("/create/{id}")
    public String updateCategory(@PathVariable Long id,@ModelAttribute Category category)
    {
        category.setId(id);
        categoryService.saveCategory(category);
        return "redirect:/category";
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id)
    {
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }
}
