package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.News;
import com.example.demo.repository.NewsRepository;

@Service
public class NewsService {
  
  private final NewsRepository newsRepository;

  public NewsService(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  public void join(News news) {
    newsRepository.addNews(news);
  }

  public News findOne(Long newsId) {
    return newsRepository.findById(newsId)
      .orElseThrow(() -> new IllegalStateException("해당 뉴스가 존재하지 않습니다."));
  }
}
