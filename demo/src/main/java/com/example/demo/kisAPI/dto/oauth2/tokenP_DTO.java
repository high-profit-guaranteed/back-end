package com.example.demo.kisAPI.dto.oauth2;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

public class tokenP_DTO {

  /**
   * 계좌 정보를 통해 접근 토큰을 받기 위한 요청 DTO
   */
  public static class ReqBody {
    @NonNull
    private final String grant_type;
    @NonNull
    private final String appkey;
    @NonNull
    private final String appsecret;

    private ReqBody(@NonNull String grant_type, @NonNull String appkey, @NonNull String appsecret) {
      this.grant_type = grant_type;
      this.appkey = appkey;
      this.appsecret = appsecret;
    }

    @NonNull
    public static ReqBody from(@NonNull Account account) {
      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      if (APP_KEY == null || APP_SECRET == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      return new ReqBody("client_credentials", APP_KEY, APP_SECRET);
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
  public static class ResBody {
    private final String access_token;
    private final String token_type;
    private final Long expires_in;
    private final String access_token_token_expired;

    public ResBody(String access_token, String token_type, Long expires_in, String access_token_token_expired) {
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
      return "GetTokenResDto [access_token=" + access_token + ", token_type=" + token_type + ", expires_in="
          + expires_in
          + ", access_token_token_expired=" + access_token_token_expired + "]";
    }
  }
}
