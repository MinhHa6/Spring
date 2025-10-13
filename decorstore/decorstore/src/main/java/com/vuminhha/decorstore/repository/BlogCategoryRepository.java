package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory,Long> {
}
