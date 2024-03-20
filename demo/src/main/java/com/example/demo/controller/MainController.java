package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;

import ch.qos.logback.core.model.Model;

@Controller
public class MainController {

  private final MemberService memberService;
  public MainController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/")
  public String mainPage(Model model, @SessionAttribute(name = "id", required = false) Long id) {
    Member member = memberService.getSigninMember(id);
    if (member == null) {
      return "redirect:/signin";
    }

    return "redirect:/home";
  }

}
