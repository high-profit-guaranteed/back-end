package com.example.demo.kisAPI.classes.oauth2;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.Req;
import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.Res;
import com.example.demo.kisAPI.interfaces.oauth2.Approval_Interface;

public class Approval implements Approval_Interface {

  @NonNull
  private final String url;

  public Approval(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/oauth2/tokenP");
  }

  @Override
  @NonNull
  public Res post(@NonNull Req request) {
    WebClient webClient = WebClient.builder().baseUrl(this.url).defaultHeader("content-type", "application/json")
        .build();

    Res responseBody = webClient.post()
        .bodyValue(request)
        .retrieve()
        .bodyToMono(Res.class)
        .block();

    if (responseBody == null) {
      throw new IllegalStateException("응답이 올바르지 않습니다.");
    }
    
    return responseBody;
  }
}
