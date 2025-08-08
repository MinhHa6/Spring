package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Category;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
