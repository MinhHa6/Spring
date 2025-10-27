package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final ProductService productService;
    public UserController (ProductService productService)
    {
        this.productService=productService;
    }
    @GetMapping
    public String home()
    {
        return "users/home";
    }
    // san pham noi bat o home
    @GetMapping("/product")
    public String productHome(Model model )
    {
        List<Product> featuredProducts = productService.getFeaturedProducts();
        model.addAttribute("featuredProducts", featuredProducts);
        return "users/home";
    }
}
