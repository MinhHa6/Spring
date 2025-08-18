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
    // lay tat ca product
    @GetMapping
    public List<Product> getAllProduct()
    {
        return productService.getAll();
    }
    // tao moi product
    @PostMapping
    public Product createProduct(@RequestBody ProductCreateRequest request)
    {
        return productService.CreateProduct(request);
    }
    // lay product theo id
    @GetMapping("/{id}")
    public Product getProductId(@PathVariable Long id)
    {
        return productService.getProductId(id);
    }
    // update product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request)
    {
        return productService.updateProductById(id,request);
    }
    // xoa product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return "Delete have been";
    }

}
