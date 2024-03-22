package com.example.demo.repository.springDataJpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Account;
import com.example.demo.repository.AccountRepository;

public interface SpringDataJpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {

}