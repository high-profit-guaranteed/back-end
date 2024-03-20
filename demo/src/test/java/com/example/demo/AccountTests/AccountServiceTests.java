package com.example.demo.AccountTests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Account;
import com.example.demo.domain.Member;
import com.example.demo.repository.memoryRepository.MemoryAccountRepository;
import com.example.demo.repository.memoryRepository.MemoryMemberRepository;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class AccountServiceTests {

  @Value("${fakeAccount.appkey}")
  String appkey;
  @Value("${fakeAccount.secretkey}")
  String secretkey;

  private final MemberService memberService = new MemberService(new MemoryMemberRepository());
  private final AccountService accountService = new AccountService(new MemoryAccountRepository());

  @AfterEach
  public void afterEach() {
    accountService.clearStore();
    memberService.clearStore();
  }

  @Test
  @DisplayName("환경변수 테스트")
  public void 환경변수_테스트() {
    // Given
    // When
    // Then
    System.out.println(appkey);
    System.out.println(secretkey);
    Assertions.assertThat(appkey).isNotNull();
    Assertions.assertThat(secretkey).isNotNull();
  }
  
  @Test
  @DisplayName("계좌 생성 테스트")
  public void 계좌_생성() {
    // Given
    Member member = new Member("uid", "email", "password", "name");

    // When
    memberService.join(member);
    Account account = new Account(memberService.findByUid(member.getUid()).getId(), 50101503, "모의", "fake", appkey, secretkey);
    accountService.join(account);
    account = accountService.findByAccountNumber(account.getAccountNumber());

    // Then
    Assertions.assertThat(account.getAccountNumber()).isEqualTo(50101503);
    Assertions.assertThat(account.getAccountName()).isEqualTo("모의");
    Assertions.assertThat(account.getAccountType()).isEqualTo("fake");
    Assertions.assertThat(account.getAPP_KEY()).isEqualTo(appkey);
    Assertions.assertThat(account.getAPP_SECRET()).isEqualTo(secretkey);
  }


  @Test
  @DisplayName("액세스 토큰 생성 테스트")
  public void 액세스_토큰_생성() {
    // Given
    Member member = new Member("uid", "email", "password", "name");

    // When
    memberService.join(member);
    Account account = new Account(memberService.findByUid(member.getUid()).getId(), 50101503, "모의", "fake", appkey, secretkey);
    accountService.join(account);
    Account result = accountService.getAccessToken(accountService.findByAccountNumber(account.getAccountNumber()).getId());

    // Then
    log.info(result.getAPP_KEY() + " " + result.getAPP_SECRET() + " " + result.getAccessToken() + " " + result.getAccessTokenExpired());
    Assertions.assertThat(result).isNotNull();
    // Assertions.assertThat(account.getAccessToken()).isNotNull();
  }
  
}
