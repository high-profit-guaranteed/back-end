package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.domain.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.DTO.GetBalanceResBodyDto;

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

  private String getUrlString(String accountType) {
    if (accountType.equals("real")) {
      return "https://openapi.koreainvestment.com:9443";
    } else if (accountType.equals("fake")) {
      return "https://openapivts.koreainvestment.com:29443";
    } else {
      return null;
    }
  }

  /**
   * 계좌 정보를 통해 접근 토큰을 받아옵니다.
   */
  public Optional<Account> getAccessToken(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    String baseUrl = getUrlString(account.getAccountType());
    if (baseUrl == null) {
      return null;
    }
    String url = baseUrl + "/oauth2/tokenP";
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
      
      log.info("접근 토큰 생성 결과: {}", responseBody);

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

  // public void clearStore() {
  // accountRepository.clearStore();
  // }

  /**
   * 계좌 정보를 통해 해외주식 잔고를 조회합니다.
   */
  public GetBalanceResBodyDto getAccountInfo(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("해당 계좌가 존재하지 않습니다."));

    String baseUrl = getUrlString(account.getAccountType());
    if (baseUrl == null) {
      return null;
    }
    String url = baseUrl + "/uapi/overseas-stock/v1/trading/inquire-balance";

    GetBalanceReqHeaderDto getBalanceReqHeaderDto = GetBalanceReqHeaderDto.from(account);
    GetBalanceReqQueryParamDto getBalanceReqBodyDto = GetBalanceReqQueryParamDto.from(account);

    if (getBalanceReqHeaderDto == null || getBalanceReqBodyDto == null) {
      return null;
    }

    WebClient webClient = WebClient.builder().baseUrl(url).defaultHeaders(httpHeaders -> {
      httpHeaders.set("authorization", getBalanceReqHeaderDto.getAuthorization());
      httpHeaders.set("appkey", getBalanceReqHeaderDto.getAppkey());
      httpHeaders.set("appsecret", getBalanceReqHeaderDto.getAppsecret());
      httpHeaders.set("tr_id", getBalanceReqHeaderDto.getTr_id());
    }).build();

    try {
      // GET 요청
      GetBalanceResBodyDto responseBody = webClient.get()
          .uri(uriBuilder -> uriBuilder.queryParam("CANO", getBalanceReqBodyDto.getCANO())
              .queryParam("ACNT_PRDT_CD", getBalanceReqBodyDto.getACNT_PRDT_CD())
              .queryParam("OVRS_EXCG_CD", getBalanceReqBodyDto.getOVRS_EXCG_CD())
              .queryParam("TR_CRCY_CD", getBalanceReqBodyDto.getTR_CRCY_CD())
              .queryParam("CTX_AREA_FK200", getBalanceReqBodyDto.getCTX_AREA_FK200())
              .queryParam("CTX_AREA_NK200", getBalanceReqBodyDto.getCTX_AREA_NK200())
              .build())
          .retrieve()
          .bodyToMono(GetBalanceResBodyDto.class)
          .block();

      log.info("해외주식 잔고 조회 결과: {}", responseBody);

      return responseBody;
    } catch (WebClientResponseException e) {
      log.error("error: {}", e.getResponseBodyAsString());
      return null;
    }
  }
}

/**
 * 계좌 정보를 통해 접근 토큰을 받기 위한 요청 DTO
 */
class GetTokenReqDto {
  @NonNull
  private final String grant_type;
  @NonNull
  private final String appkey;
  @NonNull
  private final String appsecret;

  private GetTokenReqDto(@NonNull String grant_type, @NonNull String appkey, @NonNull String appsecret) {
    this.grant_type = grant_type;
    this.appkey = appkey;
    this.appsecret = appsecret;
  }

  public static GetTokenReqDto from(Account account) {
    String APP_KEY = account.getAPP_KEY();
    String APP_SECRET = account.getAPP_SECRET();
    if (APP_KEY == null || APP_SECRET == null) {
      return null;
    }
    return new GetTokenReqDto("client_credentials", APP_KEY, APP_SECRET);
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
    return "GetTokenResDto [access_token=" + access_token + ", token_type=" + token_type + ", expires_in=" + expires_in
        + ", access_token_token_expired=" + access_token_token_expired + "]";
  }
}

class GetBalanceReqHeaderDto {
  @NonNull
  private final String authorization;
  @NonNull
  private final String appkey;
  @NonNull
  private final String appsecret;
  @NonNull
  private final String tr_id;

  private GetBalanceReqHeaderDto(@NonNull String authorization, @NonNull String appkey, @NonNull String appsecret,
      @NonNull String tr_id) {
    this.authorization = authorization;
    this.appkey = appkey;
    this.appsecret = appsecret;
    this.tr_id = tr_id;
  }

  public static GetBalanceReqHeaderDto from(Account account) {
    String tr_id;
    if (account.getAccountType().equals("real")) {
      tr_id = "TTTS3012R";
    } else if (account.getAccountType().equals("fake")) {
      tr_id = "VTTS3012R";
    } else {
      return null;
    }

    String APP_KEY = account.getAPP_KEY();
    String APP_SECRET = account.getAPP_SECRET();
    if (APP_KEY == null || APP_SECRET == null) {
      return null;
    }

    return new GetBalanceReqHeaderDto("Bearer " + account.getAccessToken(), APP_KEY, APP_SECRET, tr_id);
  }

  public String getAuthorization() {
    return authorization;
  }

  public String getAppkey() {
    return appkey;
  }

  public String getAppsecret() {
    return appsecret;
  }

  public String getTr_id() {
    return tr_id;
  }
}

class GetBalanceReqQueryParamDto {
  @NonNull
  private final String CANO;
  @NonNull
  private final String ACNT_PRDT_CD;
  @NonNull
  private final String OVRS_EXCG_CD;
  @NonNull
  private final String TR_CRCY_CD;
  @Nullable
  private final String CTX_AREA_FK200;
  @Nullable
  private final String CTX_AREA_NK200;

  private GetBalanceReqQueryParamDto(@NonNull String CANO, @NonNull String ACNT_PRDT_CD, @NonNull String OVRS_EXCG_CD,
      @NonNull String TR_CRCY_CD, String CTX_AREA_FK200, String CTX_AREA_NK200) {
    this.CANO = CANO;
    this.ACNT_PRDT_CD = ACNT_PRDT_CD;
    this.OVRS_EXCG_CD = OVRS_EXCG_CD;
    this.TR_CRCY_CD = TR_CRCY_CD;
    this.CTX_AREA_FK200 = CTX_AREA_FK200;
    this.CTX_AREA_NK200 = CTX_AREA_NK200;
  }

  public static GetBalanceReqQueryParamDto from(Account account) {
    String CANO = String.valueOf(account.getAccountNumber());
    if (CANO == null) {
      return null;
    }
    return new GetBalanceReqQueryParamDto(CANO, "01", "NASD", "USD", "", "");
  }

  public GetBalanceReqQueryParamDto from(Account account, String CTX_AREA_FK200, String CTX_AREA_NK200) {
    String CANO = String.valueOf(account.getAccountNumber());
    if (CANO == null) {
      return null;
    }
    return new GetBalanceReqQueryParamDto(CANO, "01", "NASD", "USD", CTX_AREA_FK200,
        CTX_AREA_NK200);
  }

  public String getCANO() {
    return CANO;
  }

  public String getACNT_PRDT_CD() {
    return ACNT_PRDT_CD;
  }

  public String getOVRS_EXCG_CD() {
    return OVRS_EXCG_CD;
  }

  public String getTR_CRCY_CD() {
    return TR_CRCY_CD;
  }

  public String getCTX_AREA_FK200() {
    return CTX_AREA_FK200;
  }

  public String getCTX_AREA_NK200() {
    return CTX_AREA_NK200;
  }
}

class GetBalanceResHeaderDto {
  private final String content_type;
  private final String tr_id;
  private final String tr_cont;
  private final String gt_uid;

  public GetBalanceResHeaderDto(String content_type, String tr_id, String tr_cont, String gt_uid) {
    this.content_type = content_type;
    this.tr_id = tr_id;
    this.tr_cont = tr_cont;
    this.gt_uid = gt_uid;
  }

  public String getContent_type() {
    return content_type;
  }

  public String getTr_id() {
    return tr_id;
  }

  public String getTr_cont() {
    return tr_cont;
  }

  public String getGt_uid() {
    return gt_uid;
  }
}
