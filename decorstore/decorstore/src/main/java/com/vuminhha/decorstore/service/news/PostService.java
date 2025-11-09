package com.vuminhha.decorstore.service.news;


import com.vuminhha.decorstore.entity.BlogPost;

import java.util.List;

public interface PostService {
    List<BlogPost> getAll();
    List<BlogPost>getAllActive();
    BlogPost getBlogById(Long id);
    List<BlogPost>getByCategory(Long categoryId);
    List<BlogPost>searchByKeyword(String keyword);
    BlogPost saveBlog(BlogPost blogPost);
    void deleteBlogById(Long id);
}
