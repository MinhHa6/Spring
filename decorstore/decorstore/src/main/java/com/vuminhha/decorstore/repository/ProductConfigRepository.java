package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.ProductConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductConfigRepository extends JpaRepository<ProductConfig,Long> {
    List<ProductConfig> findByProductId(Long productId);
}
