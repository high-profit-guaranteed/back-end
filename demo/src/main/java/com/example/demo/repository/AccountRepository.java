package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Account;

public interface AccountRepository {
  public Account addAccount(Account account);
  public Optional<Account> findById(Long id);
  public Optional<Account> findByAccount_number(int accountNumber);
  public List<Account> findAll();
  public List<Account> findByMember_id(Long memberId);
}
