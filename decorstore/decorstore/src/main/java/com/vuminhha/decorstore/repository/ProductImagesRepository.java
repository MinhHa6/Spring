package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImage,Long> {
}
