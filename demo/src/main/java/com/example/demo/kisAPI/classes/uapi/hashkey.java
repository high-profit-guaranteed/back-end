package com.example.demo.kisAPI.classes.uapi;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.uapi.hashkey_DTO.ReqBody;
import com.example.demo.kisAPI.dto.uapi.hashkey_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.hashkey_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.uapi.hashkey_Interface;

public class hashkey implements hashkey_Interface {

  @NonNull
  private final String url;

  public hashkey(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/uapi/hashkey");
  }

  @Override
  @NonNull
  public ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody) {
    WebClient webClient = WebClient.builder().baseUrl(this.url).defaultHeaders(header -> {
      header.set("content-type", reqHeader.getContent_type());
      header.set("appkey", reqHeader.getAppkey());
      header.set("appsecret", reqHeader.getAppsecret());
    }).build();

    ResBody responseBody = webClient.post()
        .bodyValue(reqBody)
        .retrieve()
        .bodyToMono(ResBody.class)
        .block();

    if (responseBody == null) {
      throw new IllegalStateException("응답이 올바르지 않습니다.");
    }

    return responseBody;
  }
}
