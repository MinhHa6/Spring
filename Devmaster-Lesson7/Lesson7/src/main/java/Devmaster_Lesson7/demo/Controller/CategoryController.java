package Devmaster_Lesson7.demo.Controller;

import Devmaster_Lesson7.demo.Service.CategoryService;
import Devmaster_Lesson7.demo.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String listCategories (Model model)
    {
        model.addAttribute("categories",categoryService.getAllCategories());
        return "category/category-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("category",new Category());
        return "category/category-form";
    }
    @PostMapping("/create")
    public String saveCategory (@ModelAttribute ("category")Category category)
    {
        categoryService.saveCategory(category);
        return "redirect:/category";
    }
   @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id")Long id,Model model)
   {
       model.addAttribute("category",categoryService.getCategoryId(id).orElse(null));
       return "category/category-form";
   }
   @PostMapping("/create/{id}")
    public  String updatecategory(@PathVariable Long id,@ModelAttribute Category category)
   {
       category.setId(id);
       categoryService.saveCategory(category);
       return "redirect:/category";
   }
   @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id")Long id)
   {
       categoryService.deleteCategory(id);
       return "redirect:/category";
   }
}
