package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.domain.Member;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;

@Controller
public class APIController {

  private final MemberService memberService;
  private final AccountService accountService;

  public APIController(MemberService memberService, AccountService accountService) {
    this.memberService = memberService;
    this.accountService = accountService;
  }

  @GetMapping("/api/getAccounts")
  public ResponseEntity<Accounts> getMethodName(@SessionAttribute(name = "id", required = false) Long id) {
    
    // check session start
    if (id == null) return ResponseEntity.status(HttpStatus.OK).body(null);
    Member member = memberService.getSigninMember(id);
    if (member == null) return ResponseEntity.status(HttpStatus.OK).body(null);
    // check session end

    Accounts accountNames = new Accounts();
    accountService.findByMemberId(member.getId()).forEach(account -> {
      accountNames.addAccount(account.getAccountName());
    });

    return ResponseEntity.ok(accountNames);
  }

}

class Accounts {
  private List<String> accounts;

  public Accounts() {
    this.accounts = new ArrayList<String>();
  }

  public List<String> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<String> accounts) {
    this.accounts = accounts;
  }

  public void addAccount(String account) {
    this.accounts.add(account);
  }
}