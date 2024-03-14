package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;


@Controller
public class MemberController {

  private final MemberService memberService;
  
  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/signup")
  public String signup(Model model) {
      return "signup";
  }

  @PostMapping("/signup")
  public String create(MemberFrom form) {

    Member member = new Member();
    member.setUid(form.getUid());
    member.setEmail(form.getEmail());
    member.setPassword(form.getPassword());
    member.setName(form.getName());

    memberService.join(member);

    return "redirect:/";
  }

  @GetMapping("/members")
  public String list(Model model) {
    List<Member> members = memberService.findMembers();
    model.addAttribute("members", members);
    return "/memberList";
  }
  
}
