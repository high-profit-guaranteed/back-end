package com.example.demo.kisAPI.dto.uapi;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

public class hashkey_DTO {
  public static class ReqHeader {
    @NonNull
    private final String content_type;
    @NonNull
    private final String appkey;
    @NonNull
    private final String appsecret;

    private ReqHeader(@NonNull String content_type, @NonNull String appkey, @NonNull String appsecret) {
      this.content_type = content_type;
      this.appkey = appkey;
      this.appsecret = appsecret;
    }

    @NonNull
    public static ReqHeader from(@NonNull Account account) {
      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      if (APP_KEY == null || APP_SECRET == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      return new ReqHeader("application/json; charset=utf-8", APP_KEY, APP_SECRET);
    }

    public String getContent_type() {
      return content_type;
    }

    public String getAppkey() {
      return appkey;
    }

    public String getAppsecret() {
      return appsecret;
    }
  }

  public static class ReqBody {
    @NonNull
    private final Object JsonBody;

    private ReqBody(@NonNull Object JsonBody) {
      this.JsonBody = JsonBody;
    }

    @NonNull
    public static ReqBody from(@NonNull Object JsonBody) {
      return new ReqBody(JsonBody);
    }

    public Object getJsonBody() {
      return JsonBody;
    }
  }

  public static class ResBody {
    private final Object JsonBody;
    private final String HASH;

    public ResBody(Object JsonBody, String HASH) {
      this.JsonBody = JsonBody;
      this.HASH = HASH;
    }

    public Object getJsonBody() {
      return JsonBody;
    }

    public String getHASH() {
      return HASH;
    }

    @Override
    public String toString() {
      return "ResBody [JsonBody=" + JsonBody + ", HASH=" + HASH + "]";
    }
  }
}
