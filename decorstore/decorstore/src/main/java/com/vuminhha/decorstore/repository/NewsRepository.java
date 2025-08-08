package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
