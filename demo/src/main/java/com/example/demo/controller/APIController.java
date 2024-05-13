package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.domain.Member;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Controller
public class APIController {

  private final MemberService memberService;
  private final AccountService accountService;

  public APIController(MemberService memberService, AccountService accountService) {
    this.memberService = memberService;
    this.accountService = accountService;
  }

  @GetMapping("/api/getAccounts")
  public ResponseEntity<Accounts> getAccounts(@SessionAttribute(name = "id", required = false) Long id) {
    
    // check session start
    if (id == null) return ResponseEntity.status(HttpStatus.OK).body(null);
    Member member = memberService.getSigninMember(id);
    if (member == null) return ResponseEntity.status(HttpStatus.OK).body(null);
    // check session end

    Accounts accounts = new Accounts();
    accountService.findByMemberId(member.getId()).forEach(account -> {
      accounts.addAccount(account.getAccountName(), account.getId());
    });

    return ResponseEntity.ok(accounts);
  }

  @GetMapping("/api/getBalance")
  public ResponseEntity<Balance> getBalance(@SessionAttribute(name = "id", required = false) Long id) {
      
      // check session start
      if (id == null) return ResponseEntity.status(HttpStatus.OK).body(null);
      Member member = memberService.getSigninMember(id);
      if (member == null) return ResponseEntity.status(HttpStatus.OK).body(null);
      // check session end
  
      Balance balance = new Balance();
      balance.setBalance(accountService.getBalance(member.getId()));
  
      return ResponseEntity.ok(balance);
    }

}

@Data
@NoArgsConstructor
class Accounts {
  private List<AccountObj> accounts;

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
@NoArgsConstructor
class Balance {
  private Long balance;
}