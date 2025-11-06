package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost,Long> {
    // Tim blog theo category
    List<BlogPost> findByCategoryId(Long categoryId);
    //Tim blog theo keyword (title hoac content
    List<BlogPost>findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    // Timm blog active
    List<BlogPost>findByIsActiveTrue();
}
