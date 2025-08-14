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
    public Product CreateProduct(ProductCreateRequest request)
    {
        Product product= new Product();
        BeanUtils.copyProperties(request, product); // copy toàn bộ các field trùng tên

        // Nếu muốn auto set ngày tạo & ngày cập nhật
        if (product.getCreatedDate() == null) {
            product.setCreatedDate(LocalDateTime.now());
        }
        product.setUpdatedDate(LocalDateTime.now());

        return productRepository.save(product);
    }
    public Product updateProductById(Long id, ProductUpdateRequest request)
    {
        Product product=getProductId(id);
        BeanUtils.copyProperties(request, product); // copy toàn bộ các field trùng tên

        // Nếu muốn auto set ngày tạo & ngày cập nhật
        if (product.getCreatedDate() == null) {
            product.setCreatedDate(LocalDateTime.now());
        }
        product.setUpdatedDate(LocalDateTime.now());

        return productRepository.save(product);
    }
    public List<Product> getAll()
    {
        return productRepository.findAll();
    }
    public Product getProductId(Long id)
    {
        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Product"));
    }
    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }

}
