package com.example.demo.kisAPI.classes.uapi.domestic_stock.v1.trading;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.kisAPI.UrlGenerator;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_balance_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_balance_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_balance_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.uapi.domestic_stock.v1.trading.inquire_balance_Interface;

public class inquire_balance implements inquire_balance_Interface {

  @NonNull
  private final String url;

  public inquire_balance(boolean isVirtual) {
    this.url = new UrlGenerator().getUrl(isVirtual, "/uapi/domestic-stock/v1/trading/inquire-balance");
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
            .queryParam("AFHR_FLPR_YN", reqQueryParam.getAFHR_FLPR_YN())
            .queryParam("OFL_YN", reqQueryParam.getOFL_YN())
            .queryParam("INQR_DVSN", reqQueryParam.getINQR_DVSN())
            .queryParam("UNPR_DVSN", reqQueryParam.getUNPR_DVSN())
            .queryParam("FUND_STTL_ICLD_YN", reqQueryParam.getFUND_STTL_ICLD_YN())
            .queryParam("FNCG_AMT_AUTO_RDPT_YN", reqQueryParam.getFNCG_AMT_AUTO_RDPT_YN())
            .queryParam("PRCS_DVSN", reqQueryParam.getPRCS_DVSN())
            .queryParam("CTX_AREA_FK100", reqQueryParam.getCTX_AREA_FK100())
            .queryParam("CTX_AREA_NK100", reqQueryParam.getCTX_AREA_NK100())
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
