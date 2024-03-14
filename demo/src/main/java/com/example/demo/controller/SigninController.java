package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;


@Controller
public class SigninController {
  
  @GetMapping("/signin")
  public String signin(Model model) {
      return "signin";
  }
  
}
