package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImage,Long> {
    // Lay tat ca anh theo productId
    List<ProductImage> findByProductId (Long productId);
}
