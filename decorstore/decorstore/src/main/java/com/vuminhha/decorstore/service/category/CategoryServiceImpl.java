package com.vuminhha.decorstore.service.category;

import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryServiceImpl implements CategoryService{
     CategoryRepository categoryRepository;
     @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
@Override
    public Category getCategoryId(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }
@Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
@Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    @Override
    public List<Category>searchByName(String keyword)
    {
        return categoryRepository.findByNameContainingIgnoreCase(keyword);
    }
}
