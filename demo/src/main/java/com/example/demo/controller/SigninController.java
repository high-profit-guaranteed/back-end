package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigninController {

  // private final MemberService memberService;

  // public SigninController(MemberService memberService) {
  //   this.memberService = memberService;
  // }

  @GetMapping("/signin")
  public String signinPage(Model model) {
    return "signin";
  }

  // @PostMapping("/signin")
  // public String signin(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult,
  //     HttpServletRequest httpServletRequest, Model model) {
  //   model.addAttribute("loginType", "session-login");
  //   model.addAttribute("pageName", "세션 로그인");

  //   Member member = memberService.signin(loginRequest.getUid(), loginRequest.getPassword());

  //   if 

  //   return "";
  // }

}

// class LoginRequest {
//   private String uid;
//   private String password;

//   public String getUid() {
//     return uid;
//   }

//   public void setUid(String uid) {
//     this.uid = uid;
//   }

//   public String getPassword() {
//     return password;
//   }

//   public void setPassword(String password) {
//     this.password = password;
//   }
// }
