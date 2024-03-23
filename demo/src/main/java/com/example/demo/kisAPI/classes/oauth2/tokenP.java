package com.example.demo.kisAPI.classes.oauth2;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.oauth2.tokenP_DTO.ReqBody;
import com.example.demo.kisAPI.dto.oauth2.tokenP_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.oauth2.tokenP_Interface;

public class tokenP implements tokenP_Interface {

  @NonNull
  private final String url;

  public tokenP(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/oauth2/tokenP");
  }

  @Override
  @NonNull
  public ResBody post(@NonNull ReqBody request) {
    WebClient webClient = WebClient.builder().baseUrl(this.url).defaultHeader("content-type", "application/json")
        .build();

    ResBody responseBody = webClient.post()
        .bodyValue(request)
        .retrieve()
        .bodyToMono(ResBody.class)
        .block();

    if (responseBody == null) {
      throw new IllegalStateException("응답이 올바르지 않습니다.");
    }

    return responseBody;
  }
}
