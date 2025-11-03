package com.vuminhha.decorstore.service;


import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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

    // Lay ra san pham noi bat
    public List<Product>getFeaturedProducts()
    {
        return productRepository.findByIsFeaturedTrueAndIsActiveTrue();
    }
    // lay 4 san pham cung danh muc tru san pham hien tai
    public List<Product>getFindTop4ByCategory_IdAndIdNot(Long categoryId,Long productId)
    {
        return productRepository.findTop4ByCategory_IdAndIdNot(categoryId,productId);
    }
    // Load product với đầy đủ ảnh và cấu hình
    @Transactional(readOnly = true)
    public Product getProductWithDetails(Long id) {
        // Load ảnh phụ trước
        Product product = productRepository.findByIdWithImages(id)
                .orElseThrow(() -> new RuntimeException("Not Product"));

        // Load cấu hình sau (trong cùng transaction nên không bị lỗi)
        productRepository.findByIdWithConfigs(id);

        return product;
    }
}
