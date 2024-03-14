package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Account;

@Repository
public class MemoryAccountRepository implements AccountRepository{

  private static Map<Long, Account> store = new HashMap<>();
  private static long sequence = 0L;

  @Override
  public Account addAccount(Account account) {
    account.setId(++sequence);
    store.put(account.getId(), account);
    return account;
  }

  @Override
  public Optional<Account> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Optional<Account> findByAccount_number(int accountNumber) {
    return store.values().stream()
      .filter(account -> account.getAccountNumber() == accountNumber)
      .findAny();
  }

  @Override
  public List<Account> findAll() {
    return List.copyOf(store.values());
  }

  @Override
  public List<Account> findByMember_id(Long memberId) {
    return store.values().stream()
      .filter(account -> account.getMemberId() == memberId)
      .toList();
  }
  
}
