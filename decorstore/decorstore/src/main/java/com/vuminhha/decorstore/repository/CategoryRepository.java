package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByNameContainingIgnoreCase(String name);
    // phan trang
    // Tìm danh mục theo tên có phân trang
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
