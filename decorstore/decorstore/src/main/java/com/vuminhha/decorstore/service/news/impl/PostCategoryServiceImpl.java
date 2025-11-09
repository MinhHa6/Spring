package com.vuminhha.decorstore.service.news.impl;

import com.vuminhha.decorstore.entity.BlogCategory;
import com.vuminhha.decorstore.repository.BlogCategoryRepository;
import com.vuminhha.decorstore.service.news.PostCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostCategoryServiceImpl implements PostCategoryService {
    private static final Logger log = LoggerFactory.getLogger(PostCategoryService.class);
    private final BlogCategoryRepository blogCategoryRepository;
    public PostCategoryServiceImpl(BlogCategoryRepository blogCategoryRepository)
    {
        this.blogCategoryRepository=blogCategoryRepository;
    }

    // Lấy tất cả danh mục
    @Override
    public List<BlogCategory> getAll() {
        log.info("Fetching all post categories");
        return blogCategoryRepository.findAll();
    }

    // Lấy chi tiết theo ID
    @Override
    public BlogCategory getById(Long id) {
        log.info("Fetching post category by id: {}", id);
        return blogCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post category not found with id: " + id));
    }

    // Lấy chi tiết theo slug
    @Override
    public BlogCategory getBySlug(String slug) {
        log.info("Fetching post category by slug: {}", slug);
        return blogCategoryRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Post category not found with slug: " + slug));
    }

    // Tạo danh mục mới
    @Override
    public BlogCategory create(BlogCategory blogCategory) {
        return blogCategoryRepository.save(blogCategory);
    }


    // Xóa danh mục
    @Override
    public void delete(Long id) {
        log.info("Deleting post category id: {}", id);
        blogCategoryRepository.deleteById(id);
    }
}
