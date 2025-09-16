package com.vuminhha.decorstore.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Index {
    @GetMapping
    public String admin()
    {
        return "admin/index";
    }

    @GetMapping("/products")
    public String product(Model model) {
        // sau này bạn có thể lấy danh sách sản phẩm từ DB và add vào model
        // model.addAttribute("products", productService.findAll());

        return "admin/product-list"; // load admin/product.html
    }
    @GetMapping("/category")
    public String category()
    {
        return "admin/category-list";
    }
    @GetMapping("/login")
    public String login()
    {
        return "admin/login";
    }
}
