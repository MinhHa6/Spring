package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //  inject CategoryRepository
    public CategoryService (CategoryRepository categoryRepository)
    {
         this.categoryRepository=categoryRepository;
    }
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getCategoryId(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
