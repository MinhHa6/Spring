package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.dto.productRequest.ProductCreateRequest;
import com.vuminhha.decorstore.dto.productRequest.ProductUpdateRequest;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public List<Product> getAllProduct()
    {
        return productService.getAll();
    }
    @PostMapping
    public Product createProduct(@RequestBody ProductCreateRequest request)
    {
        return createProduct(request);
    }
    @GetMapping("/products/{id}")
    public Product getProductId(@PathVariable Long id)
    {
        return productService.getProductId(id);
    }
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Long ids, @RequestBody ProductUpdateRequest request)
    {
        return productService.updateProductById(ids,request);
    }
    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return "Delete have been";
    }

}
