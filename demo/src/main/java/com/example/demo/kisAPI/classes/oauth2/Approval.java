package com.example.demo.kisAPI.classes.oauth2;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.ReqBody;
import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.oauth2.Approval_Interface;

import io.netty.handler.logging.LogLevel;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

public class Approval implements Approval_Interface {

  @NonNull
  private final String url;

  public Approval(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/oauth2/Approval");
  }

  @Override
  @NonNull
  public ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody) {
    WebClient webClient = WebClient.builder().baseUrl(this.url).defaultHeaders(header -> {
      header.set("content-type", reqHeader.getContent_type());
    })
    .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)))
    .build();

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
