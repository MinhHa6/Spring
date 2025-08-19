package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.dto.productRequest.ProductCreateRequest;
import com.vuminhha.decorstore.dto.productRequest.ProductUpdateRequest;
import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    // lay tat ca product
    @GetMapping
   public String listProduct(Model model)
    {
        model.addAttribute("products",productService.getAll());
        return "admin/product-list";
    }
    // tao moi product
    @GetMapping("/create")
   public String showCreateForm(Model model)
    {
        model.addAttribute("product",new Product());
        return "admin/product-form";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model)
    {
        model.addAttribute("product",productService.getProductId(id));
        return "admin/product-form";
    }
    @PostMapping("/create")
    public String savaProduct(@ModelAttribute("product")Product product)
    {
        productService.saveProduct(product);
        return "redirect:/products";
    }
    @PostMapping("/create/{id}")
    public String updateProduct(@PathVariable Long id,@ModelAttribute Product product)
    {
        product.setId(id);
        productService.saveProduct(product);
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
            public String deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return "redirect:/products";

    }

}
