package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.Account;
import com.example.demo.domain.Member;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    Member member = new Member(form.getUid(), form.getEmail().split("@")[0], form.getEmail().split("@")[1], form.getPassword(), form.getName());

    memberService.join(member);

    return "redirect:/";
  }

  @GetMapping("/members")
  public String list(Model model) {
    List<Member> members = memberService.findMembers();
    model.addAttribute("members", members);
    return "/memberList";
  }

  @GetMapping("/member/{uid}")
  public String getMember(Model model, @PathVariable("uid") String uid) {

    log.info(uid);

    if (uid == null) {
      return "redirect:/";
    }
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