package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.BlogPost;
import com.vuminhha.decorstore.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     *Lay tat ca blog active chi hien thi bai dang
     */
    public List<BlogPost>getAllActive()
    {
        return blogPostRepository.findAll().stream()
                .filter(BlogPost ::getActive)
                .sorted((a,b)->b.getCreatedDate().compareTo(a.getCreatedDate()))
                .collect(Collectors.toList());
    }
    // lay blog theo dia chi Id
    public BlogPost getBlogById(Long id)
    {
        return blogPostRepository.findById(id).orElseThrow(()-> new RuntimeException("Blog not found with by id:"+id));
    }

    /**
     *Lay blog theo category
     */
    public List<BlogPost>getByCategory(Long categoryId)
    {
        return blogPostRepository.findByCategoryId(categoryId);
    }
    // tim kiem blog theo keyword
    public List<BlogPost>searchByKeyword(String keyword)
    {
        return blogPostRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword,keyword);
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
