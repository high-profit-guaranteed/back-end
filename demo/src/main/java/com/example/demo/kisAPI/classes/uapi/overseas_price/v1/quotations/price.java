package com.example.demo.kisAPI.classes.uapi.overseas_price.v1.quotations;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations.price_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations.price_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations.price_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.uapi.overseas_price.v1.quotations.price_Interface;

public class price implements price_Interface {

  @NonNull
  private final String url;

  public price(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/uapi/overseas-price/v1/quotations/price");
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
        .uri(uriBuilder -> uriBuilder.queryParam("AUTH", reqQueryParam.getAUTH())
            .queryParam("EXCD", reqQueryParam.getEXCD())
            .queryParam("SYMB", reqQueryParam.getSYMB())
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
