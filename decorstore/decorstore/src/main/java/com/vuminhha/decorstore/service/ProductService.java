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
    private  final ProductRepository productRepository;

    //  inject CategoryRepository
    public ProductService (ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    //lay tat ca product
    public List<Product> getAll()
    {
        return productRepository.findAll();
    }
    // Them hoac cap nhat san pham
    public Product saveProduct (Product product)
    {
        System.out.println(product);
        return productRepository.save(product);
    }
    // Lay san pham theo Id
    public Product getProductId(Long id)
    {
        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Product"));
    }
    // xoa product theo id
    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }
    // tim kiem theo ten (Chua tu khoa khong phan biet hoa thuong )
    public List<Product> searchByName(String keyword)
    {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

   //Tim kiem theo danh muc
    public List<Product>findByCategory(Long categoryId)
    {
        return productRepository.findByCategoryId(categoryId);
    }

}
