package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.News;

public interface NewsRepository {
  public News addNews(News news);
  public Optional<News> findById(Long id);
  public List<News> findAll();
  public List<News> findByAuthor(String author);
  public List<News> findByTitle(String title);
  public List<News> findByContent(String content);
  public List<News> findByDate(String date);
}
