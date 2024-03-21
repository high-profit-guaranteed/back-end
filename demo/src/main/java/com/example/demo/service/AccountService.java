package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.domain.Account;
import com.example.demo.repository.AccountRepository;

import jakarta.validation.constraints.NotNull;

@Service
public class AccountService {
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void join(Account account) {
    accountRepository.save(account);
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

  /**
   * 계좌 정보를 통해 접근 토큰을 받아옵니다.
   * 
   * @param accountId 계좌 ID
   * @return 업데이트된 Account 객체
   */
  public Optional<Account> getAccessToken(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    String url = "https://openapivts.koreainvestment.com:29443/oauth2/tokenP";
    WebClient webClient = WebClient.builder().baseUrl(url).defaultHeader("content-type", "application/json").build();

    GetTokenReqDto getTokenReqDto = GetTokenReqDto.from(account);
    if (getTokenReqDto == null) {
      return Optional.empty();
    }

    try {
      // POST 요청
      GetTokenResDto responseBody = webClient.post()
          .bodyValue(getTokenReqDto) // requestBody 정의
          .retrieve() // 응답 정의 시작
          .bodyToMono(GetTokenResDto.class) // 응답 데이터 정의
          .block(); // 동기식 처리

      updateAccessToken(accountId,
          responseBody.getAccess_token(),
          responseBody.getAccess_token_token_expired());
      // Account updatedAccount = accountRepository.findById(accountId)
      // .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
      // updatedAccount.setAccessToken(responseBody.getAccess_token());
      // updatedAccount.setAccessTokenExpired(responseBody.getAccess_token_token_expired());
      // accountRepository.save(updatedAccount);

      return accountRepository.findById(accountId);
    } catch (WebClientResponseException e) {
      return Optional.empty();
    }
  }

  public Account findByAccountNumber(int accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
  }

  public void updateAccessToken(Long accountId, String accessToken, String accessTokenExpired) {
    Account updatedAccount = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
    updatedAccount.setAccessToken(accessToken);
    updatedAccount.setAccessTokenExpired(accessTokenExpired);
    accountRepository.save(updatedAccount);
  }

  // public void clearStore() {
  // accountRepository.clearStore();
  // }
}

/**
 * 계좌 정보를 통해 접근 토큰을 받기 위한 요청 DTO
 */
class GetTokenReqDto {
  @NotNull
  private final String grant_type;
  @NotNull
  private final String appkey;
  @NotNull
  private final String appsecret;

  private GetTokenReqDto(String grant_type, String appkey, String appsecret) {
    this.grant_type = grant_type;
    this.appkey = appkey;
    this.appsecret = appsecret;
  }

  public static GetTokenReqDto from(Account account) {
    return new GetTokenReqDto("client_credentials", account.getAPP_KEY(), account.getAPP_SECRET());
  }

  public String getGrant_type() {
    return grant_type;
  }

  public String getAppkey() {
    return appkey;
  }

  public String getAppsecret() {
    return appsecret;
  }
}

/**
 * 접근 토큰을 받기 위한 응답 DTO
 */
class GetTokenResDto {
  private final String access_token;
  private final String token_type;
  private final Long expires_in;
  private final String access_token_token_expired;

  public GetTokenResDto(String access_token, String token_type, Long expires_in, String access_token_token_expired) {
    this.access_token = access_token;
    this.token_type = token_type;
    this.expires_in = expires_in;
    this.access_token_token_expired = access_token_token_expired;
  }

  public String getAccess_token() {
    return access_token;
  }

  public String getToken_type() {
    return token_type;
  }

  public Long getExpires_in() {
    return expires_in;
  }

  public String getAccess_token_token_expired() {
    return access_token_token_expired;
  }

  @Override
  public String toString() {
    return "MessageDto{" +
        ", access_token='" + access_token + '\'' +
        ", token_type='" + token_type + '\'' +
        ", expires_in=" + expires_in +
        ", access_token_token_expired='" + access_token_token_expired + '\'' +
        '}';
  }

}