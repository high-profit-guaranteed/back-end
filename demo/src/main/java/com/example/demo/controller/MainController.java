package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;

@Controller
public class MainController {

  @GetMapping("/")
  public String mainPage(Model model) {
    return "redirect:/signin";
  }

}
