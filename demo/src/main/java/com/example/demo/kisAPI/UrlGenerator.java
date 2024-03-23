package com.example.demo.kisAPI;

import org.springframework.lang.NonNull;

public class UrlGenerator {
  private String domain;
  private String v_domain;

  public UrlGenerator() {
    this.domain = "https://openapi.koreainvestment.com:9443";
    this.v_domain = "https://openapivts.koreainvestment.com:29443";
  }

  private String getDomain(boolean isVirtual) {
    if (isVirtual) {
      return v_domain;
    } else {
      return domain;
    }
  }

  @NonNull public String getUrl(boolean isVirtual, String path) {
    return getDomain(isVirtual) + path;
  }
}
