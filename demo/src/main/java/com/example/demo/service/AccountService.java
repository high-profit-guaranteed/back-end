package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.domain.Account;
import com.example.demo.kisAPI.classes.oauth2.tokenP;
import com.example.demo.kisAPI.classes.uapi.domestic_stock.v1.trading.order_cash;
import com.example.demo.kisAPI.classes.uapi.overseas_stock.v1.trading.order;
import com.example.demo.kisAPI.dto.oauth2.tokenP_DTO;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO;
import com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations.price_DTO;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.order_DTO;
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
      tokenP_DTO.ResBody responseBody = new tokenP(account.isVirtual())
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
   * 계좌 정보를 통해 국내주식 잔고를 조회합니다.
   */
  public com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_balance_DTO.ResBody getAccountInfoDomestic(
      Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      // GET 요청
      com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_balance_DTO.ResBody responseBody = new com.example.demo.kisAPI.classes.uapi.domestic_stock.v1.trading.inquire_balance(
          account.isVirtual())
          .get(com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_balance_DTO.ReqHeader.from(account),
              com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_balance_DTO.ReqQueryParam
                  .from(account));

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }

  /**
   * 계좌 정보를 통해 해외주식 잔고를 조회합니다.
   */
  public com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ResBody getAccountInfoOverseas(
      Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      // GET 요청
      com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ResBody responseBody = new com.example.demo.kisAPI.classes.uapi.overseas_stock.v1.trading.inquire_balance(
          account.isVirtual())
          .get(com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ReqHeader.from(account),
              com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ReqQueryParam
                  .from(account));

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }

  /**
   * 계좌 정보를 통해 국내주식을 주문합니다.
   */
  public order_cash_DTO.ResBody orderDomestic(Long accountId,
      @NonNull order_cash_DTO.ReqBody reqBody, boolean isBuy) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    log.info(reqBody.toString());

    try {
      // POST 요청
      order_cash_DTO.ResBody responseBody = new order_cash(
          account.isVirtual())
          .post(order_cash_DTO.ReqHeader.from(account, isBuy), reqBody);

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }

  /**
   * 계좌 정보를 통해 해외주식을 주문합니다.
   */
  public order_DTO.ResBody orderOverseas(Long accountId,
      @NonNull order_DTO.ReqBody reqBody, boolean isBuy) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      // POST 요청
      order_DTO.ResBody responseBody = new order(
          account.isVirtual())
          .post(order_DTO.ReqHeader.from(account, isBuy), reqBody);

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }

  public price_DTO.ResBody getPriceOverseas(Long accountId, @NonNull String code) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      // GET 요청
      price_DTO.ResBody responseBody = new com.example.demo.kisAPI.classes.uapi.overseas_price.v1.quotations.price(
          account.isVirtual())
          .get(price_DTO.ReqHeader.from(account),
              price_DTO.ReqQueryParam.from("NAS", code));

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }
}
