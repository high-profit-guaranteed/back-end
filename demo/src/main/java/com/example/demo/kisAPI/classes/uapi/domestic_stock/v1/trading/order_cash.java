package com.example.demo.kisAPI.classes.uapi.domestic_stock.v1.trading;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO.ReqBody;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.uapi.domestic_stock.v1.trading.order_cash_Interface;

public class order_cash implements order_cash_Interface {

  @NonNull
  private final String url;

  public order_cash(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/uapi/domestic-stock/v1/trading/order-cash");
  }

  @Override
  @NonNull
  public ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody) {
    WebClient webClient = WebClient.builder().baseUrl(this.url).defaultHeaders(header -> {
      header.set("content-type", reqHeader.getContent_type());
      header.set("authorization", reqHeader.getAuthorization());
      header.set("appkey", reqHeader.getAppkey());
      header.set("appsecret", reqHeader.getAppsecret());
      header.set("tr_id", reqHeader.getTr_id());
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
