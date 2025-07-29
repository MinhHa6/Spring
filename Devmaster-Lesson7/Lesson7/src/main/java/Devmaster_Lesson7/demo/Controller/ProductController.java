package Devmaster_Lesson7.demo.Controller;

import Devmaster_Lesson7.demo.Service.CategoryService;
import Devmaster_Lesson7.demo.Service.ProductService;
import Devmaster_Lesson7.demo.entity.Category;
import Devmaster_Lesson7.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public String listCategories (Model model)
    {
        model.addAttribute("products",productService.getAllProduct());
        return "category/product-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("product",new Product());
        return "category/product-form";
    }
    @PostMapping("/create")
    public String saveProduct (@ModelAttribute("product")Product product)
    {
        productService.saveProduct(product);
        return "redirect:/product";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id")Long id, Model model)
    {
        model.addAttribute("product",productService.findbyId(id).orElse(null));
        return "category/product-form";
    }
    @PostMapping("/create/{id}")
    public  String updateProduct(@PathVariable Long id,@ModelAttribute Product product)
    {
        product.setId(id);
        productService.saveProduct(product);
        return "redirect:/product";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id")Long id)
    {
        productService.deleteProduct(id);
        return "redirect:/product";
    }
}
