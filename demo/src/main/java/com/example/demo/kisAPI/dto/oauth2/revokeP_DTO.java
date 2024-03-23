package com.example.demo.kisAPI.dto.oauth2;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

public class revokeP_DTO {
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

  public static class ResBody {
    private final String code;
    private final String message;

    public ResBody(String code, String message) {
      this.code = code;
      this.message = message;
    }

    public String getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }

    @Override
    public String toString() {
      return "ResBody [code=" + code + ", message=" + message + "]";
    }
  }
}
