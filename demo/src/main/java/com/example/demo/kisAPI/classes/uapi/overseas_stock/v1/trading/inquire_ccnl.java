package com.example.demo.kisAPI.classes.uapi.overseas_stock.v1.trading;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_ccnl_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_ccnl_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_ccnl_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.uapi.overseas_stock.v1.trading.inquire_ccnl_Interface;

public class inquire_ccnl implements inquire_ccnl_Interface {
  @NonNull
  private final String url;

  public inquire_ccnl(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/uapi/overseas-stock/v1/trading/inquire-ccnl");
  }

  @Override
  @NonNull
  public ResponseEntity<ResBody> get(@NonNull ReqHeader reqHeader, @NonNull ReqQueryParam reqQueryParam) {
    WebClient webClient = WebClient.builder().baseUrl(this.url).defaultHeaders(header -> {
      header.set("content-type", reqHeader.getContent_type());
      header.set("authorization", reqHeader.getAuthorization());
      header.set("appkey", reqHeader.getAppkey());
      header.set("appsecret", reqHeader.getAppsecret());
      header.set("tr_id", reqHeader.getTr_id());
    }).build();

    ResponseEntity<ResBody> response = webClient.get()
        .uri(uriBuilder -> uriBuilder.queryParam("CANO", reqQueryParam.getCANO())
            .queryParam("ACNT_PRDT_CD", reqQueryParam.getACNT_PRDT_CD())
            .queryParam("PDNO", reqQueryParam.getPDNO())
            .queryParam("ORD_STRT_DT", reqQueryParam.getORD_STRT_DT())
            .queryParam("ORD_END_DT", reqQueryParam.getORD_END_DT())
            .queryParam("SLL_BUY_DVSN", reqQueryParam.getSLL_BUY_DVSN())
            .queryParam("CCLD_NCCS_DVSN", reqQueryParam.getCCLD_NCCS_DVSN())
            .queryParam("OVRS_EXCG_CD", reqQueryParam.getOVRS_EXCG_CD())
            .queryParam("SORT_SQN", reqQueryParam.getSORT_SQN())
            .queryParam("ORD_DT", reqQueryParam.getORD_DT())
            .queryParam("ORD_GNO_BRNO", reqQueryParam.getORD_GNO_BRNO())
            .queryParam("ODNO", reqQueryParam.getODNO())
            .queryParam("CTX_AREA_NK200", reqQueryParam.getCTX_AREA_NK200())
            .queryParam("CTX_AREA_FK200", reqQueryParam.getCTX_AREA_FK200())
            .build())
        .retrieve()
        .toEntity(ResBody.class)
        .block();

    if (response == null) {
      throw new IllegalStateException("응답이 올바르지 않습니다.");
    }

    return response;
  }

  
}
