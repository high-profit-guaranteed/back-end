package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.News;
import com.example.demo.service.NewsService;

@Controller
public class NewsController {
  private final NewsService newsService;

  public NewsController(NewsService newsService) {
    this.newsService = newsService;
  }

  @GetMapping("/news")
  public String getNewsList(Model model) {
    List<News> newsList = newsService.findNews();
    model.addAttribute("newsList", newsList);
    return "/newsList";
  }

  @PostMapping("/news")
  public String createDummyNews() {
    int length = newsService.findNews().size() + 1;
    News news = new News();
    news.setTitle("[dumy title " + length + "]");
    news.setContent("[dumy content " + length + "]");
    news.setAuthor("[dumy author " + length + "]");
    news.setCreatedAt("[dumy createdAt " + length + "]");
    news.setUpdatedAt("[dumy updatedAt " + length + "]");
    news.setLink("[dumy link " + length + "]");
    newsService.join(news);

    return "redirect:/news";
  }
  
}
