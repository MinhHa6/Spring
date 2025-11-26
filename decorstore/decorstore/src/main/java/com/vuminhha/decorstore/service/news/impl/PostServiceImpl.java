package com.vuminhha.decorstore.service.news.impl;

import com.vuminhha.decorstore.entity.BlogPost;
import com.vuminhha.decorstore.repository.BlogPostRepository;
import com.vuminhha.decorstore.service.news.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PostServiceImpl implements PostService {
     BlogPostRepository blogPostRepository;
    // lay tat ca blog
    @Override
    public List<BlogPost> getAll()
    {
        return blogPostRepository.findAll();
    }

    /**
     *Lay tat ca blog active chi hien thi bai dang
     */
    @Override
    public List<BlogPost>getAllActive()
    {
        return blogPostRepository.findAll().stream()
                .filter(BlogPost ::getIsActive)
                .sorted((a,b)->b.getCreatedDate().compareTo(a.getCreatedDate()))
                .collect(Collectors.toList());
    }
    // lay blog theo dia chi Id
    @Override
    public BlogPost getBlogById(Long id)
    {
        return blogPostRepository.findById(id).orElseThrow(()-> new RuntimeException("Blog not found with by id:"+id));
    }

    /**
     *Lay blog theo category
     */
    @Override
    public List<BlogPost>getByCategory(Long categoryId)
    {
        return blogPostRepository.findByCategoryId(categoryId);
    }
    // tim kiem blog theo keyword
    @Override
    public List<BlogPost>searchByKeyword(String keyword)
    {
        return blogPostRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword,keyword);
    }
    // them hoac cap nhat blog
    @Override
    public BlogPost saveBlog(BlogPost blogPost)
    {
        return blogPostRepository.save(blogPost);
    }
    // xoa blog theo Id
    @Override
    public void deleteBlogById(Long id)
    {
        blogPostRepository.deleteById(id);
    }
}
