package com.example.demo.kisAPI.classes.uapi.domestic_stock.v1.trading;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_daily_ccld_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_daily_ccld_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_daily_ccld_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.uapi.domestic_stock.v1.trading.inquire_daily_ccld_Interface;

public class inquire_daily_ccld implements inquire_daily_ccld_Interface {

  @NonNull
  private final String url;

  public inquire_daily_ccld(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/uapi/domestic-stock/v1/trading/inquire-daily-ccld");
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
        .uri(uriBuilder -> {
          uriBuilder.queryParam("CANO", reqQueryParam.getCANO());
          uriBuilder.queryParam("ACNT_PRDT_CD", reqQueryParam.getACNT_PRDT_CD());
          uriBuilder.queryParam("INQR_STRT_DT", reqQueryParam.getINQR_STRT_DT());
          uriBuilder.queryParam("INQR_END_DT", reqQueryParam.getINQR_END_DT());
          uriBuilder.queryParam("SLL_BUY_DVSN_CD", reqQueryParam.getSLL_BUY_DVSN_CD());
          uriBuilder.queryParam("INQR_DVSN", reqQueryParam.getINQR_DVSN());
          uriBuilder.queryParam("PDNO", reqQueryParam.getPDNO());
          uriBuilder.queryParam("CCLD_DVSN", reqQueryParam.getCCLD_DVSN());
          uriBuilder.queryParam("ORD_GNO_BRNO", reqQueryParam.getORD_GNO_BRNO());
          uriBuilder.queryParam("ODNO", reqQueryParam.getODNO());
          uriBuilder.queryParam("INQR_DVSN_3", reqQueryParam.getINQR_DVSN_3());
          uriBuilder.queryParam("INQR_DVSN_1", reqQueryParam.getINQR_DVSN_1());
          uriBuilder.queryParam("CTX_AREA_FK100", reqQueryParam.getCTX_AREA_FK100());
          uriBuilder.queryParam("CTX_AREA_NK100", reqQueryParam.getCTX_AREA_NK100());
          return uriBuilder.build();
        })
        .retrieve()
        .bodyToMono(ResBody.class)
        .block();

    if (responseBody == null) {
      throw new IllegalStateException("응답이 올바르지 않습니다.");
    }

    return responseBody;
  }
  
}
