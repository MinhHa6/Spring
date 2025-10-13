package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    public List<News> getAll ()
    {
        return newsRepository.findAll();
    }
    // lay tin tuc theo dia chi id
    public News getNewById(Long id )
    {
        return newsRepository.findById(id).orElseThrow(() -> new RuntimeException("New null "));
    }
    // luu thong tin
    public News saveNew (News news)
    {
        return newsRepository.save(news);
    }
    // xoa tin tuc theo dia chi id
    public void deleteNews (Long id)
    {
        newsRepository.deleteById(id);
    }
}
