package com.vuminhha.decorstore.service.news;


import com.vuminhha.decorstore.entity.BlogCategory;

import java.util.List;

public interface PostCategoryService {
    List<BlogCategory> getAll();
    BlogCategory getById(Long id);
    BlogCategory getBySlug(String slug);
    BlogCategory create(BlogCategory blogCategory);
    void delete(Long id);
}
