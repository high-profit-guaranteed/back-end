package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

  public Account getAccessToken(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    String url = "https://openapivts.koreainvestment.com:29443/oauth2/tokenP";
    WebClient webClient = WebClient.builder().baseUrl(url).defaultHeader("content-type", "application/json").build();

    GetTokenDto messageDto = new GetTokenDto("client_credentials", account.getAPP_KEY(), account.getAPP_SECRET());

    // POST 요청
    GetTokenDto responseBody = webClient.post()
        .bodyValue(messageDto) // requestBody 정의
        .retrieve() // 응답 정의 시작
        .bodyToMono(GetTokenDto.class) // 응답 데이터 정의
        .block(); // 동기식 처리

    accountRepository.updateAccessToken(accountId, responseBody.getAccess_token(), responseBody.getAccess_token_token_expired());
    return account;
  }

  public Account findByAccountNumber(int accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
  }

  public void updateAccessToken(Long accountId, String accessToken, String accessTokenExpired) {
    accountRepository.updateAccessToken(accountId, accessToken, accessTokenExpired);
  }

  public void clearStore() {
    accountRepository.clearStore();
  }
}

class GetTokenDto {
  private String grant_type;
  private String appkey;
  private String appsecret;

  private String access_token;
  private String token_type;
  private Long expires_in;
  private String access_token_token_expired;

  public GetTokenDto(String grant_type, String appkey, String appsecret) {
    this.grant_type = grant_type;
    this.appkey = appkey;
    this.appsecret = appsecret;
  }

  public String getGrant_type() {
    return grant_type;
  }

  public void setGrant_type(String grant_type) {
    this.grant_type = grant_type;
  }

  public String getAppkey() {
    return appkey;
  }

  public void setAppkey(String appkey) {
    this.appkey = appkey;
  }

  public String getAppsecret() {
    return appsecret;
  }

  public void setAppsecret(String appsecret) {
    this.appsecret = appsecret;
  }

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public String getToken_type() {
    return token_type;
  }

  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }

  public Long getExpires_in() {
    return expires_in;
  }

  public void setExpires_in(Long expires_in) {
    this.expires_in = expires_in;
  }

  public String getAccess_token_token_expired() {
    return access_token_token_expired;
  }

  public void setAccess_token_token_expired(String access_token_token_expired) {
    this.access_token_token_expired = access_token_token_expired;
  }

  @Override
  public String toString() {
    return "MessageDto{" +
        "grant_type='" + grant_type + '\'' +
        ", appkey='" + appkey + '\'' +
        ", appsecret='" + appsecret + '\'' +
        ", access_token='" + access_token + '\'' +
        ", token_type='" + token_type + '\'' +
        ", expires_in=" + expires_in +
        ", access_token_token_expired='" + access_token_token_expired + '\'' +
        '}';
  }

}