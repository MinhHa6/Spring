package com.vuminhha.decorstore.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail")
public class Product_detail {
    @GetMapping
    public String productDetail()
    {
        return "users/product_detail";
    }
}
