package com.example.demo.kisAPI.dto.oauth2;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

import lombok.Data;
import lombok.NoArgsConstructor;

public class Approval_DTO {
  public static class ReqHeader {
    @NonNull
    private final String content_type;

    private ReqHeader(@NonNull String content_type) {
      this.content_type = content_type;
    }

    public ReqHeader() {
      this("application/json; utf-8");
    }

    public String getContent_type() {
      return content_type;
    }
  }

  public static class ReqBody {
    @NonNull
    private final String grant_type;
    @NonNull
    private final String appkey;
    @NonNull
    private final String secretkey;

    private ReqBody(@NonNull String grant_type, @NonNull String appkey, @NonNull String secretkey) {
      this.grant_type = grant_type;
      this.appkey = appkey;
      this.secretkey = secretkey;
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

    public String getSecretkey() {
      return secretkey;
    }
  }

  @Data
  @NoArgsConstructor
  public static class ResBody {
    private String approval_key;
  }
}
