package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.service.CategoryService;
import com.vuminhha.decorstore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final CategoryService categoryService;
    private final ProductService productService;
    public ShopController (ProductService productService,CategoryService categoryService)
    {
        this.productService=productService;
        this.categoryService=categoryService;
    }
    @GetMapping
    public String viewShopPage(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "categoryId", required = false) Long categoryId,
                               Model model) {

        List<Product> products;
        List<Category> categories = categoryService.getAll();

        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchByName(keyword);
            model.addAttribute("searchKeyword", keyword);
        } else if (categoryId != null) {
            products = productService.findByCategory(categoryId);
            model.addAttribute("selectedCategoryId", categoryId);
        } else {
            products = productService.getAll();
        }

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "users/shop"; // Giao diện shop.html (Decor Store)
    }
    @GetMapping("/{id}")
    public String viewProductDetail(@PathVariable("id")Long id,Model model)
    {
        Product product=productService.getProductId(id);
        if (product == null)
        {
            return "redirect:/shop";
        }
        model.addAttribute("product",product);
        return "users/product_detail";
    }

}
