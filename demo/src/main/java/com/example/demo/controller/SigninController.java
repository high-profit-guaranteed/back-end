package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class SigninController {

  private final MemberService memberService;

  public SigninController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/signin")
  public String signinPage(Model model) {
    return "signin";
  }

  @PostMapping("/signin")
  public String signin(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult,
      HttpServletRequest httpServletRequest, Model model) {
    model.addAttribute("loginType", "session-login");
    model.addAttribute("pageName", "세션 로그인");

    Member member = memberService.signin(loginRequest.getUid(), loginRequest.getPassword());

    if (member == null) {
      return "signin";
    }

    // 로그인 성공 => 세션 생성

    // 세션을 생성하기 전에 기존의 세션 파기
    httpServletRequest.getSession().invalidate();
    HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
    // 세션에 userId를 넣어줌
    session.setAttribute("id", member.getId());
    session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

    return "redirect:/home";
  }

}

class LoginRequest {
  private String uid;
  private String password;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
