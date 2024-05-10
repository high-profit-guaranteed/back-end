package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;

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

    if (bindingResult.hasErrors()) {
      return "signin";
    }

    String uid = loginRequest.getUid();
    String password = loginRequest.getPassword();

    if (uid == null || uid.isEmpty() || password == null || password.isEmpty()) {
      return "signin";
    }

    Member member = memberService.signin(uid, password);

    if (member == null) {
      return "signin";
    }

    // 로그인 성공 => 세션 생성

    // 세션을 생성하기 전에 기존의 세션 파기
    httpServletRequest.getSession().invalidate();
    HttpSession session = httpServletRequest.getSession(true); // Session이 없으면 생성
    // 세션에 userId를 넣어줌
    session.setAttribute("id", member.getId());
    session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

    return "redirect:/home";
  }

  @PostMapping("api/signin")
  public ResponseEntity<String> apiSignin(@RequestBody Login login, BindingResult bindingResult,
      HttpServletRequest httpServletRequest, Model model) {
    model.addAttribute("loginType", "session-login");
    model.addAttribute("pageName", "세션 로그인");

    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("bindingResult.hasErrors()");
    }

    // Map<String, String> body = bodyToMap(requestBody.toString());

    // String uid = body.get("uid");
    // String password = body.get("password");

    String uid = login.getUid();
    String password = login.getPassword();

    if (uid == null || uid.isEmpty() || password == null || password.isEmpty()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("uid == null || uid.isEmpty() || password == null || password.isEmpty()");
    }

    Member member = memberService.signin(uid, password);

    if (member == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("member == null");
    }

    // 로그인 성공 => 세션 생성

    // 세션을 생성하기 전에 기존의 세션 파기
    httpServletRequest.getSession().invalidate();
    HttpSession session = httpServletRequest.getSession(true); // Session이 없으면 생성
    // 세션에 userId를 넣어줌
    session.setAttribute("id", member.getId());
    session.setMaxInactiveInterval(1800); // Session이 30분동안 유지
    

    // return ResponseEntity.ok("uid="+member.getId());
    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>("{'sessionId':'" + session.getAttribute("id") + "'}", httpHeaders, HttpStatus.OK);
  }

  @PostMapping("api/signup")
  public ResponseEntity<String> apiSignup(@RequestBody Signup signup, BindingResult bindingResult,
      HttpServletRequest httpServletRequest) {

    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("bindingResult.hasErrors()");
    }

    // Map<String, String> body = bodyToMap(requestBody.toString());

    // String uid = body.get("uid");
    // String password = body.get("password");

    String uid = signup.getUid();
    String password = signup.getPassword();
    String name = signup.getName();
    String email = signup.getEmail();

    String emailName = email.split("@")[0];
    String emailDomain = email.split("@")[1];

    if (uid == null || uid.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty()
        || email == null || email.isEmpty()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("uid == null || uid.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty() || email == null || email.isEmpty()");
    }

    if (emailName == null || emailDomain == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("emailName == null || emailDomain == null");
    }

    
    Member member = memberService.signup(uid, password, name, emailName, emailDomain);

    if (member == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("member == null");
    }

    // return ResponseEntity.ok("uid="+member.getId());
    return new ResponseEntity<String>("success", HttpStatus.OK);
  }

  @PostMapping("api/checkSession")
  public ResponseEntity<String> postMethodName(@RequestBody String bodyString,
      @SessionAttribute(name = "id", required = false) Long id/*, HttpServletRequest httpServletRequest*/) {
    
    // HttpSession session = httpServletRequest.getSession(false); // Session이 없으면 null return
    
    if (id == null) {
      return ResponseEntity.status(HttpStatus.OK).body("fail_session_null");
    }
    // Long sessionId = session.getAttribute("id") == null ? null : (Long) session.getAttribute("id");
    // if (sessionId == null) {
    //   return ResponseEntity.status(HttpStatus.OK).body("fail_session_null");
    // }
    Member member = memberService.getSigninMember(id);
    if (member == null) {
      return ResponseEntity.status(HttpStatus.OK).body("fail_member_null");
    }

    return ResponseEntity.status(HttpStatus.OK).body("success");
  }

  @PostMapping("api/signout")
  public ResponseEntity<String> signout(HttpServletRequest request) {
    HttpSession session = request.getSession(false); // Session이 없으면 null return
    if (session != null) {
      session.invalidate();
    }
    return ResponseEntity.status(HttpStatus.OK).body("success");
  }

  // public Map<String, String> bodyToMap(String bodyStr) {
  // Map<String, String> body = new HashMap<>();
  // String[] values = bodyStr.split("&");
  // for (String value : values) {
  // String[] pair = value.split("=");
  // if (pair.length == 2) {
  // body.put(pair[0], pair[1]);
  // }
  // }
  // return body;
  // }
}

class LoginRequest {
  @NonNull
  private final String uid;
  @NonNull
  private final String password;

  public LoginRequest(@NonNull String uid, @NonNull String password) {
    this.uid = uid;
    this.password = password;
  }

  public String getUid() {
    return uid;
  }

  public String getPassword() {
    return password;
  }
}

class Login {
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

class Signup {
  private String uid;
  private String password;
  private String name;
  private String email;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}