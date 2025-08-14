package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.dto.productRequest.ProductCreateRequest;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Product createProduct(ProductCreateRequest request)
    {
        Product product= new Product();
        product.set

    }
    public List<Product> getAll()
    {
        return productRepository.findAll();
    }

}
