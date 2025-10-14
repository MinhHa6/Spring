package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost,Long> {
}
