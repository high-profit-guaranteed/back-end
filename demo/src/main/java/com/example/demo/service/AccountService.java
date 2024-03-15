package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Account;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountService {
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void join(Account account) {
    accountRepository.addAccount(account);
  }

  public Account findOne(Long accountId) {
    return accountRepository.findById(accountId)
      .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
  }

  public List<Account> findAccounts() {
    return accountRepository.findAll();
  }

  public List<Account> findByMemberId(Long memberId) {
    return accountRepository.findByMemberId(memberId);
  }
}
