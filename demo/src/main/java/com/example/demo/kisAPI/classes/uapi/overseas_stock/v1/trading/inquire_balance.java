package com.example.demo.kisAPI.classes.uapi.overseas_stock.v1.trading;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.uapi.overseas_stock.v1.trading.inquire_balance_Interface;

public class inquire_balance implements inquire_balance_Interface {

  @NonNull
  private final String url;

  public inquire_balance(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/uapi/overseas-stock/v1/trading/inquire-balance");
  }

  @Override
  @NonNull
  public ResBody get(@NonNull ReqHeader reqHeader, @NonNull ReqQueryParam reqQueryParam) {
    WebClient webClient = WebClient.builder().baseUrl(this.url).defaultHeaders(header -> {
      header.set("authorization", reqHeader.getAuthorization());
      header.set("appkey", reqHeader.getAppkey());
      header.set("appsecret", reqHeader.getAppsecret());
      header.set("tr_id", reqHeader.getTr_id());
    }).build();

    ResBody resBody = webClient.get()
        .uri(uriBuilder -> uriBuilder.queryParam("CANO", reqQueryParam.getCANO())
            .queryParam("ACNT_PRDT_CD", reqQueryParam.getACNT_PRDT_CD())
            .queryParam("OVRS_EXCG_CD", reqQueryParam.getOVRS_EXCG_CD())
            .queryParam("TR_CRCY_CD", reqQueryParam.getTR_CRCY_CD())
            .queryParam("CTX_AREA_FK200", reqQueryParam.getCTX_AREA_FK200())
            .queryParam("CTX_AREA_NK200", reqQueryParam.getCTX_AREA_NK200())
            .build())
        .retrieve()
        .bodyToMono(ResBody.class)
        .block();

    if (resBody == null) {
      throw new IllegalStateException("응답이 올바르지 않습니다.");
    }

    return resBody;
  }
}
