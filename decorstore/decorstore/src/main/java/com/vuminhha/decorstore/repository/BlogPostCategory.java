package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostCategory extends JpaRepository<BlogPost,Long> {
}
