// package com.example.demo.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.SessionAttribute;

// import com.example.demo.domain.Member;
// import com.example.demo.service.MemberService;


// @Controller
// public class HomeController {

//   private final MemberService memberService;

//   public HomeController(MemberService memberService) {
//     this.memberService = memberService;
//   }

//   @GetMapping("/home")
//   public String getMethodName(Model model, @SessionAttribute(name = "id", required = false) Long id) {
//     model.addAttribute("loginType", "session-login");
//     model.addAttribute("pageName", "세션 로그인");

//     Member member = memberService.findOne(id);

//     if (member != null) {
//       model.addAttribute("member", member);
//     }

//     return "/home";
//   }
  
// }
