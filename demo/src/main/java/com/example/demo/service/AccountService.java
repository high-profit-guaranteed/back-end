package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.domain.Account;
import com.example.demo.kisAPI.classes.oauth2.Approval;
import com.example.demo.kisAPI.classes.oauth2.tokenP;
import com.example.demo.kisAPI.classes.tryitout.H0STCNT0;
import com.example.demo.kisAPI.classes.tryitout.HDFSCNT0;
import com.example.demo.kisAPI.classes.uapi.domestic_stock.v1.quotations.inquire_price;
import com.example.demo.kisAPI.classes.uapi.domestic_stock.v1.trading.inquire_daily_ccld;
import com.example.demo.kisAPI.classes.uapi.domestic_stock.v1.trading.order_cash;
import com.example.demo.kisAPI.classes.uapi.overseas_price.v1.quotations.price;
import com.example.demo.kisAPI.classes.uapi.overseas_stock.v1.trading.inquire_ccnl;
import com.example.demo.kisAPI.classes.uapi.overseas_stock.v1.trading.order;
import com.example.demo.kisAPI.dto.oauth2.Approval_DTO;
import com.example.demo.kisAPI.dto.oauth2.tokenP_DTO;
import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO;
import com.example.demo.kisAPI.dto.tryitout.HDFSCNT0_DTO;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.quotations.inquire_price_DTO;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_balance_DTO.ResBodyOutput2;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_daily_ccld_DTO;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO;
import com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations.price_DTO;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_ccnl_DTO;
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
      price_DTO.ResBody responseBody = new price(
          account.isVirtual())
          .get(price_DTO.ReqHeader.from(account),
              price_DTO.ReqQueryParam.from("NAS", code));

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }

  public inquire_price_DTO.ResBody getPriceDomestic(Long accountId, @NonNull String code) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      // GET 요청
      inquire_price_DTO.ResBody responseBody = new inquire_price(
          account.isVirtual())
          .get(inquire_price_DTO.ReqHeader.from(account),
              inquire_price_DTO.ReqQueryParam.from(code));

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }

  public inquire_daily_ccld_DTO.ResBody getHistoryDomestic(Long accountId, @NonNull String start, @NonNull String end) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      // GET 요청
      inquire_daily_ccld_DTO.ResBody responseBody = new inquire_daily_ccld(account.isVirtual())
          .get(inquire_daily_ccld_DTO.ReqHeader.from(account, false), inquire_daily_ccld_DTO.ReqQueryParam.from(account, start, end, null));

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }

  public inquire_ccnl_DTO.ResBody getHistoryOverseas(Long accountId, @NonNull String start, @NonNull String end) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      // GET 요청
      inquire_ccnl_DTO.ResBody responseBody = new inquire_ccnl(account.isVirtual())
          .get(inquire_ccnl_DTO.ReqHeader.from(account), inquire_ccnl_DTO.ReqQueryParam.from(account, start, end));

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }

  // public String getApproval(Long accountId) {
  //   Account account = accountRepository.findById(accountId)
  //       .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

  //   try {
  //     // GET 요청
  //     Approval_DTO.ResBody responseBody = new Approval(account.isVirtual())
  //         .post(new Approval_DTO.ReqHeader(), Approval_DTO.ReqBody.from(account));
  //     updateApprovalKey(accountId, responseBody.getApproval_key());
      
  //     return responseBody.getApproval_key();
  //   } catch (WebClientResponseException e) {
  //     log.error("error: {}", e.getResponseBodyAsString());
  //     return null;
  //   }
  // }

  public void getApproval(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    try {
      // GET 요청
      Approval_DTO.ResBody responseBody = new Approval(account.isVirtual())
          .post(new Approval_DTO.ReqHeader(), Approval_DTO.ReqBody.from(account));
      updateApprovalKey(accountId, responseBody.getApproval_key());
      
      return;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return;
    }
  }

  private void updateApprovalKey(Long accountId, String approvalKey) {
    Account updatedAccount = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
    updatedAccount.setApproval_key(approvalKey);
    accountRepository.save(updatedAccount);
  }

  public void wsDomestic(Long accountId, @NonNull String code) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    H0STCNT0_DTO.ReqHeader reqHeader = H0STCNT0_DTO.ReqHeader.from(account, true);
    H0STCNT0_DTO.ReqBody reqBody = H0STCNT0_DTO.ReqBody.from(code);

    H0STCNT0 test = new H0STCNT0(account.isVirtual());
    test.post(reqHeader, reqBody);

    log.info("wsDomestic");
  }

  public void wsOverseas(Long accountId, @NonNull String code, boolean isDay) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    HDFSCNT0_DTO.ReqHeader reqHeader = HDFSCNT0_DTO.ReqHeader.from(account, true);
    HDFSCNT0_DTO.ReqBody reqBody = HDFSCNT0_DTO.ReqBody.from(code, isDay);

    HDFSCNT0 test = new HDFSCNT0();
    test.post(reqHeader, reqBody);

    log.info("wsOverseas");
  }

  public long getBalance(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    return account.getBalance();
  }

  public void updateBalance(Long accountId, long balance) {
    Account updatedAccount = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));
    updatedAccount.setBalance(balance);
    accountRepository.save(updatedAccount);
  }

  public void setBalance(Long accountId) {
    Long balance = 0L;
    for (ResBodyOutput2 output2 : getAccountInfoDomestic(accountId).getOutput2()) {
      balance += Long.parseLong(output2.getCma_evlu_amt());
    }
    // balance += getAccountInfoOverseas(accountId).getOutput2().get
    for (com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ResBodyOutput1 output1 : getAccountInfoOverseas(accountId).getOutput1()) {
      balance += Long.parseLong(output1.getOvrs_stck_evlu_amt());
    }
  }
}
