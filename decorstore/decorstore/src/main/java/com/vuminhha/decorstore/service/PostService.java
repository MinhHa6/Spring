package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.BlogPost;
import com.vuminhha.decorstore.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final BlogPostRepository blogPostRepository;
    public  PostService(BlogPostRepository blogPostRepository)
    {
        this.blogPostRepository=blogPostRepository;
    }
    // lay tat ca blog
    public List<BlogPost> getAll()
    {
        return blogPostRepository.findAll();
    }
    // lay blog theo dia chi Id
    public BlogPost getBlogById(Long id)
    {
        return blogPostRepository.findById(id).orElseThrow(()-> new RuntimeException("Blog not found with by id:"+id));
    }
    // them hoac cap nhat blog
    public BlogPost saveBlog(BlogPost blogPost)
    {
        return blogPostRepository.save(blogPost);
    }
    // xoa blog theo Id
    public void deleteBlogById(Long id)
    {
        blogPostRepository.deleteById(id);
    }
}
