package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Account;

public interface AccountRepository {
  public Account save(Account account);

  public Optional<Account> findById(Long id);

  public Optional<Account> findByAccountNumber(int accountNumber);

  public List<Account> findAll();

  public List<Account> findByMemberId(Long memberId);

  public Optional<Account> findByAccountNumber(Long accountNumber);
}
