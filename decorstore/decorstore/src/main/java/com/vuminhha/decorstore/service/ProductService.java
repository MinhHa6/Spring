package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.dto.productRequest.ProductCreateRequest;
import com.vuminhha.decorstore.dto.productRequest.ProductUpdateRequest;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Handler;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    //lay tat ca product
    public List<Product> getAll()
    {
        return productRepository.findAll();
    }
    // Cap nhat create/update
    public Product saveProduct (Product product)
    {
        System.out.println(product);
        return productRepository.save(product);
    }
    // lay product theo id
    public Product getProductId(Long id)
    {
        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Product"));
    }
    // xoa product theo id
    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }

}
