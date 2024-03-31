package com.example.demo.kisAPI.classes.uapi.domestic_stock.v1.quotations;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.quotations.inquire_price_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.quotations.inquire_price_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.quotations.inquire_price_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.uapi.domestic_stock.v1.quotations.inquire_price_Interface;

public class inquire_price implements inquire_price_Interface {

  @NonNull
  private final String url;

  public inquire_price(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/uapi/domestic-stock/v1/quotations/inquire-price");
  }


  @Override
  @NonNull
  public ResBody get(@NonNull ReqHeader reqHeader, @NonNull ReqQueryParam reqQueryParam) {

    WebClient webClient = WebClient.builder().baseUrl(this.url).defaultHeaders(header -> {
      header.set("content-type", reqHeader.getContent_type());
      header.set("authorization", reqHeader.getAuthorization());
      header.set("appkey", reqHeader.getAppkey());
      header.set("appsecret", reqHeader.getAppsecret());
      header.set("tr_id", reqHeader.getTr_id());
    }).build();

    ResBody responseBody = webClient.get()
        .uri(uriBuilder -> uriBuilder.queryParam("FID_COND_MRKT_DIV_CODE", reqQueryParam.getFID_COND_MRKT_DIV_CODE())
            .queryParam("FID_INPUT_ISCD", reqQueryParam.getFID_INPUT_ISCD())
            .build())
        .retrieve()
        .bodyToMono(ResBody.class)
        .block();

    if (responseBody == null) {
      throw new IllegalStateException("응답이 올바르지 않습니다.");
    }

    return responseBody;
  }
  
}
