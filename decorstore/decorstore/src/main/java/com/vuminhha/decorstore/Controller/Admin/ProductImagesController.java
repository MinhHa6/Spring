package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.service.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productImages")
public class ProductImagesController {
    @Autowired
    private ProductImagesService productImagesService;
    @GetMapping
    public String listImage(Model model)
    {
        model.addAttribute("productImages",productImagesService.getAll());
        return "admin/productImages-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("productImage",new Product_Images());
        return "admin/productImage-form";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id")Long id, Model model)
    {
        model.addAttribute("productImage",productImagesService.getProductImagesById(id));
        return "admin/productImage-form";
    }
    @PostMapping("/create")
    public String saveProductImage(@ModelAttribute("productImage")Product_Images productImages )
    {
        productImagesService.saveProductImages(productImages);
        return "redirect:/productImages";
    }
    @PostMapping("/create/{id}")
    public String updateProductImage(@PathVariable Long id,@ModelAttribute Product_Images productImages)
    {
        productImages.setId(id);
        productImagesService.saveProductImages(productImages);
        return "redirect:/productImages";
    }
    @GetMapping("/delete/{id}")
    public String deleteProductImage(@PathVariable Long id)
    {
        productImagesService.deleteProductImages(id);
        return "redirect:/productImages";
    }
}
