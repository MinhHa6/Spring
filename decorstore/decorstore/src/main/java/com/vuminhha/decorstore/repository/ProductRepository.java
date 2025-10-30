package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    // Tim theo ten co chua tu khoa (ko phan biet chua hoa thuong)
    List<Product> findByNameContainingIgnoreCase(String name);
    // tim kiem theo CategoryId
    List<Product>findByCategoryId(Long categoryId);
    // Lay ra san pham noi bat
    List<Product>findByIsFeaturedTrueAndIsActiveTrue();
    // lay ra san pham tuong tu
    List<Product>findTop4ByCategory_IdAndIdNot(Long categoryId,Long excludeId);
    //  Load product với ảnh phụ
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.images " +
            "WHERE p.id = :id")
    Optional<Product> findByIdWithImages(@Param("id") Long id);
    // Query 2: Load product với cấu hình
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.productConfigs pc " +
            "LEFT JOIN FETCH pc.configurations " +
            "WHERE p.id = :id")
    Optional<Product> findByIdWithConfigs(@Param("id") Long id);
}
