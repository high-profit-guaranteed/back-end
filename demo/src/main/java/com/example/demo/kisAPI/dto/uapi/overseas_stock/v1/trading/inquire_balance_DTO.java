package com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.example.demo.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

public class inquire_balance_DTO {

  @Data
  @AllArgsConstructor
  public static class ReqHeader {
    @NonNull
    private final String authorization;
    @NonNull
    private final String appkey;
    @NonNull
    private final String appsecret;
    @NonNull
    private final String tr_id;

    @NonNull
    public static ReqHeader from(@NonNull Account account) {
      String tr_id;
      if (account.isVirtual()) {
        tr_id = "VTTS3012R";
      } else {
        tr_id = "TTTS3012R";
      }

      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      String AUTHORIZATION = "Bearer " + account.getAccessToken();
      if (APP_KEY == null || APP_SECRET == null || AUTHORIZATION == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      return new ReqHeader(AUTHORIZATION, APP_KEY, APP_SECRET, tr_id);
    }
  }

  @Data
  @AllArgsConstructor
  public static class ReqQueryParam {
    @NonNull
    private final String CANO;
    @NonNull
    private final String ACNT_PRDT_CD;
    @NonNull
    private final String OVRS_EXCG_CD;
    @NonNull
    private final String TR_CRCY_CD;
    @Nullable
    private final String CTX_AREA_FK200;
    @Nullable
    private final String CTX_AREA_NK200;

    @NonNull
    public static ReqQueryParam from(@NonNull Account account) {
      String CANO = String.valueOf(account.getAccountNumber());
      String ACNT_PRDT_CD = String.valueOf(account.getAccountProdCode());
      if (CANO == null || CANO.length() != 8 || ACNT_PRDT_CD == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }
      if (ACNT_PRDT_CD.length() == 1) {
        ACNT_PRDT_CD = "0" + ACNT_PRDT_CD;
      } else if (ACNT_PRDT_CD.length() != 2) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }
      return new ReqQueryParam(CANO, ACNT_PRDT_CD, "NASD", "USD", "", "");
    }

    @NonNull
    public static ReqQueryParam from(@NonNull Account account, @NonNull String CTX_AREA_FK200, @NonNull String CTX_AREA_NK200) {
      String CANO = String.valueOf(account.getAccountNumber());
      String ACNT_PRDT_CD = String.valueOf(account.getAccountProdCode());
      if (CANO == null || CANO.length() != 8 || ACNT_PRDT_CD == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }
      if (ACNT_PRDT_CD.length() == 1) {
        ACNT_PRDT_CD = "0" + ACNT_PRDT_CD;
      } else if (ACNT_PRDT_CD.length() != 2) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }
      return new ReqQueryParam(CANO, "01", "NASD", "USD", CTX_AREA_FK200,
          CTX_AREA_NK200);
    }
  }

  @Data
  @AllArgsConstructor
  @ToString
  public static class ResHeader {
    private final String content_type;
    private final String tr_id;
    private final String tr_cont;
    private final String gt_uid;
  }

  @Data
  @AllArgsConstructor
  @ToString
  public static class ResBody {
    private final String rt_cd;
    private final String msg_cd;
    private final String msg1;
    private final String ctx_area_fk200;
    private final String ctx_area_nk200;
    private final List<ResBodyOutput1> output1;
    private final ResBodyOutput2 output2;
  }
  
  @Data
  @AllArgsConstructor
  @ToString
  public static class ResBodyOutput1 {
    private final String cano;
    private final String acnt_prdt_cd;
    private final String prdt_type_cd;
    private final String ovrs_pdno;
    private final String ovrs_item_name;
    private final String frcr_evlu_pfls_amt;
    private final String evlu_pfls_rt;
    private final String pchs_avg_pric;
    private final String ovrs_cblc_qty;
    private final String ord_psbl_qty;
    private final String ovrs_stck_evlu_amt;
    private final String now_pric2;
    private final String tr_crcy_cd;
    private final String ovrs_excg_cd;
    private final String loan_type_cd;
    private final String loan_dt;
    private final String expd_dt;
  }

  @Data
  @AllArgsConstructor
  @ToString
  public static class ResBodyOutput2 {
    private final String frcr_pchs_amt1;
    private final String ovrs_rlzt_pfls_amt;
    private final String ovrs_tot_pfls;
    private final String rlzt_erng_rt;
    private final String tot_evlu_pfls_amt;
    private final String tot_pftrt;
    private final String frcr_buy_amt_smtl1;
    private final String ovrs_rlzt_pfls_amt2;
    private final String frcr_buy_amt_smtl2;
  }
}
