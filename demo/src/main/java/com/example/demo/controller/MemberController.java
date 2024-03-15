package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Account;
import com.example.demo.domain.Member;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;



@Controller
public class MemberController {

  private final MemberService memberService;
  private final AccountService accountService;
  
  public MemberController(MemberService memberService, AccountService accountService) {
    this.memberService = memberService;
    this.accountService = accountService;
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

  @GetMapping("/member")
  public String member(Model model, @RequestParam("uid") String uid) {
    Member member = memberService.findByUid(uid);
    model.addAttribute("member", member);

    List<Account> accounts = accountService.findByMemberId(member.getId());
    model.addAttribute("accounts", accounts);
    return "member";
  }
  
  
}

class MemberFrom {
  private String uid;
  private String email;
  private String password;
  private String name;
  
  public String getUid() {
    return uid;
  }
  public void setUid(String uid) {
    this.uid = uid;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}