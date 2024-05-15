package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.domain.Account;
import com.example.demo.domain.Member;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Controller
public class APIController {

  private final MemberService memberService;
  private final AccountService accountService;

  public APIController(MemberService memberService, AccountService accountService) {
    this.memberService = memberService;
    this.accountService = accountService;
  }

  @PostMapping("api/signin")
  public ResponseEntity<String> apiSignin(@RequestBody SignIn login, HttpServletRequest httpServletRequest) {

    String uid = login.getUid();
    String password = login.getPassword();

    if (uid == null || uid.isEmpty() || password == null || password.isEmpty()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // 멤버 존재 확인
    Member member = memberService.signin(uid, password);

    if (member == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // 로그인 성공 => 세션 생성

    // 세션을 생성하기 전에 기존의 세션 파기
    httpServletRequest.getSession().invalidate();
    HttpSession session = httpServletRequest.getSession(true); // Session이 없으면 생성
    // 세션에 userId를 넣어줌
    session.setAttribute("id", member.getId());
    session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>("Signin Success", httpHeaders, HttpStatus.OK);
  }

  @PostMapping("api/signup")
  public ResponseEntity<String> apiSignup(@RequestBody SignUp signup, HttpServletRequest httpServletRequest) {

    String uid = signup.getUid();
    String password = signup.getPassword();
    String name = signup.getName();
    String email = signup.getEmail();

    String emailName = email.split("@")[0];
    String emailDomain = email.split("@")[1];

    if (uid == null || uid.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty()
        || email == null || email.isEmpty()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    if (emailName == null || emailDomain == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    Member member = memberService.signup(uid, password, name, emailName, emailDomain);
    if (member == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    return new ResponseEntity<String>("SignUp Success", HttpStatus.OK);
  }

  @GetMapping("api/checkSession")
  public ResponseEntity<String> postMethodName(@SessionAttribute(name = "id", required = false) Long id) {
    if (CheckSession(id) == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    else return ResponseEntity.status(HttpStatus.OK).body("Success");
  }

  @GetMapping("api/signout")
  public ResponseEntity<String> signout(HttpServletRequest request) {

    HttpSession session = request.getSession(false); // Session이 없으면 null return
    if (session != null) session.invalidate(); // Session 파기

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("api/accounts")
  public ResponseEntity<Accounts> getAccounts(@SessionAttribute(name = "id", required = false) Long id) {

    Member member = CheckSession(id);
    if (member == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Accounts accounts = new Accounts();
    accountService.findByMemberId(member.getId()).forEach(account -> {
      accounts.addAccount(account.getAccountName(), account.getId());
    });

    return ResponseEntity.ok(accounts);
  }

  @GetMapping("api/balance")
  public ResponseEntity<Balance> getBalance(@SessionAttribute(name = "id", required = false) Long id,
      @RequestParam("accountId") Long accountId) {

    Member member = CheckSession(id);
    if (member == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Account account = accountService.findById(accountId);
    if (account == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    if (!accountService.isOwner(member.getId(), account.getId()))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Balance balance = new Balance(accountService.getBalance(account.getId()));

    return ResponseEntity.ok(balance);
  }

  private Member CheckSession(Long id) {
    if (id == null) return null;
    Member member = memberService.getSigninMember(id);
    if (member == null) return null;
    else return member;
  }
}

@Data
class SignIn {
  private String uid;
  private String password;
}

@Data
class SignUp {
  private String uid;
  private String password;
  private String name;
  private String email;
}

@Data
class Accounts {
  private List<AccountObj> accounts;

  public Accounts() {
    accounts = new ArrayList<>();
  }

  public void addAccount(String accountName, Long accountId) {
    accounts.add(new AccountObj(accountName, accountId));
  }
}

@Data
@NonNull
@AllArgsConstructor
class AccountObj {
  private String accountName;
  private Long accountId;
}

@Data
@AllArgsConstructor
class Balance {
  private Long balance;
}
