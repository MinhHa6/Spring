package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    // lay toan bo category
     public List<Category> getAll()
     {
         System.out.println(categoryRepository.findAll());
         return categoryRepository.findAll();
     }
     //lay category theo id
    public Category getCategoryId(Long id)
    {
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
    }
    // Update du lieu theo bang
    public Category saveCategory( Category category)
    {
        return categoryRepository.save(category);
    }
    //xoa Category
    public void deleteCategory( Long id )
    {
        categoryRepository.deleteById(id);
    }
}
