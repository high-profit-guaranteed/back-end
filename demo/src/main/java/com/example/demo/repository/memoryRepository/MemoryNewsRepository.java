package com.example.demo.repository.memoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.News;
import com.example.demo.repository.NewsRepository;

@Repository
public class MemoryNewsRepository implements NewsRepository {

  private static Map<Long, News> store = new HashMap<>();
  private static long sequence = 0L;

  @Override
  public News addNews(News news) {
    news.setId(++sequence);
    store.put(news.getId(), news);
    return news;
  }

  @Override
  public Optional<News> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public List<News> findAll() {
    return List.copyOf(store.values());
  }

  @Override
  public List<News> findByAuthor(String author) {
    return store.values().stream()
      .filter(news -> news.getAuthor().equals(author))
      .toList();
  }

  // TODO: 특정 제목 검색 기능 구현
  @Override
  public List<News> findByTitle(String title) {
    return store.values().stream()
      .filter(news -> news.getTitle().equals(title))
      .toList();
  }

  // TODO: 특정 내용 검색 기능 구현
  @Override
  public List<News> findByContent(String content) {
    return store.values().stream()
      .filter(news -> news.getContent().equals(content))
      .toList();
  }

  // TODO: 특정 날짜 검색 기능 구현
  @Override
  public List<News> findByDate(String date) {
    return store.values().stream()
      .filter(news -> news.getCreatedAt().equals(date))
      .toList();
  }
  
}
