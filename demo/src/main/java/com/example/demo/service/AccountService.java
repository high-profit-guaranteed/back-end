package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.domain.Account;
import com.example.demo.kisAPI.classes.oauth2.tokenP;
import com.example.demo.kisAPI.classes.uapi.overseas_stock.v1.trading.inquire_balance;
import com.example.demo.kisAPI.dto.oauth2.tokenP_DTO;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO;
import com.example.demo.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
   */
  public Optional<Account> getAccessToken(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      tokenP_DTO.ResBody responseBody = new tokenP(account.getAccountType().equals("fake"))
          .post(tokenP_DTO.ReqBody.from(account));

      updateAccessToken(accountId,
          responseBody.getAccess_token(),
          responseBody.getAccess_token_token_expired());

      return accountRepository.findById(accountId);
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return Optional.empty();
    }
  }

  public Account findByAccountNumber(int accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
  }

  private void updateAccessToken(Long accountId, String accessToken, String accessTokenExpired) {
    Account updatedAccount = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
    updatedAccount.setAccessToken(accessToken);
    updatedAccount.setAccessTokenExpired(accessTokenExpired);
    accountRepository.save(updatedAccount);
  }

  /**
   * 계좌 정보를 통해 해외주식 잔고를 조회합니다.
   */
  public inquire_balance_DTO.ResBody getAccountInfo(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      // GET 요청
      inquire_balance_DTO.ResBody responseBody = new inquire_balance(account.getAccountType().equals("fake"))
          .get(inquire_balance_DTO.ReqHeader.from(account),
              inquire_balance_DTO.ReqQueryParam.from(account));

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }
}
