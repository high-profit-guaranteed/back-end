package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;



@Controller
public class AccountController {
  private final AccountService accountService;
  private final MemberService memberService;

  public AccountController(AccountService accountService, MemberService memberService) {
    this.accountService = accountService;
    this.memberService = memberService;
  }

  @GetMapping("/addAccount")
  public String addAccount() {
      return "/addAccount";
  }
  
  // @GetMapping("/accounts")

  @PostMapping("/addAccount")
  public String postMethodName(AccountForm form) {
    Account account = new Account();

    account.setMemberId(memberService.findByUid(form.getMemberUid()).getId());
    account.setAccountNumber(Integer.parseInt(form.getAccountNumber()));
    account.setAccountName(form.getAccountName());
    account.setAccountType(form.getAccountType());
    account.setAPP_KEY(form.getAPP_KEY());
    account.setAPP_SECRET(form.getAPP_SECRET());

    accountService.join(account);

    return "redirect:/members";
  }
  

}

class AccountForm {
  private String memberUid;
  private String accountNumber;
  private String accountName;
  private String accountType;
  private String APP_KEY;
  private String APP_SECRET;

  public String getMemberUid() {
    return memberUid;
  }
  public void setMemberUid(String memberUid) {
    this.memberUid = memberUid;
  }
  public String getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
  public String getAccountName() {
    return accountName;
  }
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
  public String getAccountType() {
    return accountType;
  }
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
  public String getAPP_KEY() {
    return APP_KEY;
  }
  public void setAPP_KEY(String APP_KEY) {
    this.APP_KEY = APP_KEY;
  }
  public String getAPP_SECRET() {
    return APP_SECRET;
  }
  public void setAPP_SECRET(String APP_SECRET) {
    this.APP_SECRET = APP_SECRET;
  }

}
