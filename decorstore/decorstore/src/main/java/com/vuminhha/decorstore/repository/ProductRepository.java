package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    // Tim theo ten co chua tu khoa (ko phan biet chua hoa thuong)
    List<Product> findByNameContainingIgnoreCase(String name);
    // tim kiem theo CategoryId
    List<Product>findByCategoryId(Long categoryId);
    // Lay ra san pham noi bat
    List<Product>findByIsFeaturedTrueAndIsActiveTrue();
}
