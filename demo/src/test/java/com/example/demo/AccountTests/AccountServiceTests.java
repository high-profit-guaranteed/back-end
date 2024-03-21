package com.example.demo.AccountTests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Account;
import com.example.demo.domain.Member;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@SpringBootTest
public class AccountServiceTests {

  @Value("${fakeAccount.appkey}")
  String appkey;
  @Value("${fakeAccount.secretkey}")
  String secretkey;

  private final MemberService memberService;
  private final AccountService accountService;

  @Autowired
  public AccountServiceTests(MemberRepository memberRepository, AccountRepository accountRepository) {
    this.memberService = new MemberService(memberRepository);
    this.accountService = new AccountService(accountRepository);
  }


  // @AfterEach
  // public void afterEach() {
  //   accountService.clearStore();
  //   // memberService.clearStore();
  // }

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
    Member member = new Member("uid", "email", "gmail.com", "password", "name");

    // When
    memberService.join(member);
    Account account = new Account(memberService.findByUid(member.getUid()).getId(), 50101503, "모의", "fake", appkey,
        secretkey);
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
    Member member = new Member("uid", "email", "gmail.com", "password", "name");

    // When
    // Then
    memberService.join(member);
    Account account = new Account(memberService.findByUid(member.getUid()).getId(), 50101503, "모의", "fake", appkey,
        secretkey);
    accountService.join(account);

    try {
      Account result = accountService
          .getAccessToken(accountService.findByAccountNumber(account.getAccountNumber()).getId())
          .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
      log.info("\nappkey: " + result.getAPP_KEY() + "\nappsecret: " + result.getAPP_SECRET() + "\nAccess Token: "
          + result.getAccessToken() + "\nToken Expired: " + result.getAccessTokenExpired() + "\n");
      Assertions.assertThat(result.getAccessToken()).isNotNull();
    } catch (Exception e) {
      log.info("\nerror: " + e.getMessage());
    }


    // Assertions.assertThat(account.getAccessToken()).isNotNull();
  }

}
