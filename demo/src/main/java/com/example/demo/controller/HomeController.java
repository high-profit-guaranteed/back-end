package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.domain.Member;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

  private final MemberService memberService;
  private final AccountService accountService;

  public HomeController(MemberService memberService, AccountService accountService) {
    this.memberService = memberService;
    this.accountService = accountService;
  }

  @GetMapping("/home")
  public String home(Model model, @SessionAttribute(name = "id", required = false) Long id) {
    model.addAttribute("loginType", "session-login");
    model.addAttribute("pageName", "세션 로그인");

    if (id == null) {
      return "redirect:/signin";
    }
    Member member = memberService.getSigninMember(id);
    if (member == null) {
      return "redirect:/signin";
    }

    model.addAttribute("member", member);
    model.addAttribute("accounts", accountService.findByMemberId(member.getId()));
    return "signin/home";
  }

  @GetMapping("/signout")
  public String signout(Model model, HttpServletRequest request) {
    model.addAttribute("loginType", "session-login");
    model.addAttribute("pageName", "세션 로그인");

    HttpSession session = request.getSession(false); // Session이 없으면 null return
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/signin";
  }

}
