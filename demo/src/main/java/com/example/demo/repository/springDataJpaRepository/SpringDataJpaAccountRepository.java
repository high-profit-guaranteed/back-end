package com.example.demo.repository.springDataJpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Account;
import com.example.demo.repository.AccountRepository;

public interface SpringDataJpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository{

  // @Override
  // @Query("update Account a set a.accessToken = :accessToken, a.accessTokenExpired = :accessTokenExpired where a.id = :accountId")
  // public void updateAccessToken(@Param("accountId") Long accountId, @Param("accessToken") String accessToken, @Param("accessTokenExpired") String accessTokenExpired);

}