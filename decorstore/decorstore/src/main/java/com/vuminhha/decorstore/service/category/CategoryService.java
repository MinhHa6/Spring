package com.vuminhha.decorstore.service.category;


import com.vuminhha.decorstore.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getCategoryId(Long id);
    Category saveCategory(Category category);
    void deleteCategory(Long id);
    List<Category>searchByName(String keyword);
}
