package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.entity.ProductImage;
import com.vuminhha.decorstore.service.ProductImagesService;
import com.vuminhha.decorstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("productImg")
public class ImgProductController {
    @Autowired
    public ProductImagesService productImagesService;
    @Autowired
    public ProductService productService;
    /**
     * Hien thi ds anh cua 1 san pham
     */
    @GetMapping
    public String listProductImages(@PathVariable("productId")Long productId, Model model)
    {
        Product product=productService.getProductId(productId);
        List<ProductImage>images=productImagesService.getByProductId(productId);
        model.addAttribute("product",product);
        model.addAttribute("images",images);
        model.addAttribute("newImage",new ProductImage());
        return "admin/product-img-list";
    }
    /**
     * Luu anh moi
     */
    @PostMapping("/add")
    public String addProductImage(@PathVariable ("productId")Long productId, @ModelAttribute("newImage")ProductImage newImage)
    {
        Product product=productService.getProductId(productId);
        newImage.setProduct(product);
        productImagesService.saveProductImages(newImage);
        return "redirect:/productImg";
    }
    /** Hiển thị form thêm ảnh */
    @GetMapping("/add")
    public String showAddForm(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.getProductId(productId);
        model.addAttribute("product", product);
        model.addAttribute("newImage", new ProductImage());
        return "admin/product-img-form";
    }
    /**
     * Xoa anh theo id
     */
    @GetMapping("/delete/{id}")
    public  String deleteImg(@PathVariable ("productId")Long productId,@PathVariable
            ("id")Long id)
    {
        productImagesService.deleteProductImages(id);
        return "redirect:/productImg";
    }
}
