package com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.example.demo.domain.Account;

public class inquire_balance_DTO {
  public static class ReqHeader {
    @NonNull
    private final String authorization;
    @NonNull
    private final String appkey;
    @NonNull
    private final String appsecret;
    @NonNull
    private final String tr_id;

    private ReqHeader(@NonNull String authorization, @NonNull String appkey, @NonNull String appsecret,
        @NonNull String tr_id) {
      this.authorization = authorization;
      this.appkey = appkey;
      this.appsecret = appsecret;
      this.tr_id = tr_id;
    }

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
      if (APP_KEY == null || APP_SECRET == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      return new ReqHeader("Bearer " + account.getAccessToken(), APP_KEY, APP_SECRET, tr_id);
    }

    public String getAuthorization() {
      return authorization;
    }

    public String getAppkey() {
      return appkey;
    }

    public String getAppsecret() {
      return appsecret;
    }

    public String getTr_id() {
      return tr_id;
    }
  }

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

    private ReqQueryParam(@NonNull String CANO, @NonNull String ACNT_PRDT_CD, @NonNull String OVRS_EXCG_CD,
        @NonNull String TR_CRCY_CD, String CTX_AREA_FK200, String CTX_AREA_NK200) {
      this.CANO = CANO;
      this.ACNT_PRDT_CD = ACNT_PRDT_CD;
      this.OVRS_EXCG_CD = OVRS_EXCG_CD;
      this.TR_CRCY_CD = TR_CRCY_CD;
      this.CTX_AREA_FK200 = CTX_AREA_FK200;
      this.CTX_AREA_NK200 = CTX_AREA_NK200;
    }

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

    public String getCANO() {
      return CANO;
    }

    public String getACNT_PRDT_CD() {
      return ACNT_PRDT_CD;
    }

    public String getOVRS_EXCG_CD() {
      return OVRS_EXCG_CD;
    }

    public String getTR_CRCY_CD() {
      return TR_CRCY_CD;
    }

    public String getCTX_AREA_FK200() {
      return CTX_AREA_FK200;
    }

    public String getCTX_AREA_NK200() {
      return CTX_AREA_NK200;
    }
  }

  public static class ResHeader {
    private final String content_type;
    private final String tr_id;
    private final String tr_cont;
    private final String gt_uid;

    public ResHeader(String content_type, String tr_id, String tr_cont, String gt_uid) {
      this.content_type = content_type;
      this.tr_id = tr_id;
      this.tr_cont = tr_cont;
      this.gt_uid = gt_uid;
    }

    public String getContent_type() {
      return content_type;
    }

    public String getTr_id() {
      return tr_id;
    }

    public String getTr_cont() {
      return tr_cont;
    }

    public String getGt_uid() {
      return gt_uid;
    }
  }

  public static class ResBody {
    private final String rt_cd;
    private final String msg_cd;
    private final String msg1;
    private final String ctx_area_fk200;
    private final String ctx_area_nk200;
    private final List<ResBodyOutput1> output1;
    private final ResBodyOutput2 output2;

    public ResBody(String rt_cd, String msg_cd, String msg1, String ctx_area_fk200, String ctx_area_nk200,
        List<ResBodyOutput1> output1, ResBodyOutput2 output2) {
      this.rt_cd = rt_cd;
      this.msg_cd = msg_cd;
      this.msg1 = msg1;
      this.ctx_area_fk200 = ctx_area_fk200;
      this.ctx_area_nk200 = ctx_area_nk200;
      this.output1 = output1;
      this.output2 = output2;
    }

    public String getRt_cd() {
      return rt_cd;
    }

    public String getMsg_cd() {
      return msg_cd;
    }

    public String getMsg1() {
      return msg1;
    }

    public String getCtx_area_fk200() {
      return ctx_area_fk200;
    }

    public String getCtx_area_nk200() {
      return ctx_area_nk200;
    }

    public List<ResBodyOutput1> getOutput1() {
      return output1;
    }

    public ResBodyOutput2 getOutput2() {
      return output2;
    }

    @Override
    public String toString() {
      return "ResBody [rt_cd=" + rt_cd + ", msg_cd=" + msg_cd + ", msg1=" + msg1 + ", ctx_area_fk200=" + ctx_area_fk200
          + ", ctx_area_nk200=" + ctx_area_nk200 + ", output1=" + output1 + ", output2=" + output2 + "]";
    }

    
  }

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
    private final String now_pric2;
    private final String tr_crcy_cd;
    private final String ovrs_excg_cd;
    private final String loan_type_cd;
    private final String loan_dt;
    private final String expd_dt;

    public ResBodyOutput1(String cano, String acnt_prdt_cd, String prdt_type_cd, String ovrs_pdno,
        String ovrs_item_name, String frcr_evlu_pfls_amt, String evlu_pfls_rt, String pchs_avg_pric,
        String ovrs_cblc_qty,
        String now_pric2, String tr_crcy_cd, String ovrs_excg_cd, String loan_type_cd, String loan_dt, String expd_dt) {
      this.cano = cano;
      this.acnt_prdt_cd = acnt_prdt_cd;
      this.prdt_type_cd = prdt_type_cd;
      this.ovrs_pdno = ovrs_pdno;
      this.ovrs_item_name = ovrs_item_name;
      this.frcr_evlu_pfls_amt = frcr_evlu_pfls_amt;
      this.evlu_pfls_rt = evlu_pfls_rt;
      this.pchs_avg_pric = pchs_avg_pric;
      this.ovrs_cblc_qty = ovrs_cblc_qty;
      this.now_pric2 = now_pric2;
      this.tr_crcy_cd = tr_crcy_cd;
      this.ovrs_excg_cd = ovrs_excg_cd;
      this.loan_type_cd = loan_type_cd;
      this.loan_dt = loan_dt;
      this.expd_dt = expd_dt;
    }

    public String getCano() {
      return cano;
    }

    public String getAcnt_prdt_cd() {
      return acnt_prdt_cd;
    }

    public String getPrdt_type_cd() {
      return prdt_type_cd;
    }

    public String getOvrs_pdno() {
      return ovrs_pdno;
    }

    public String getOvrs_item_name() {
      return ovrs_item_name;
    }

    public String getFrcr_evlu_pfls_amt() {
      return frcr_evlu_pfls_amt;
    }

    public String getEvlu_pfls_rt() {
      return evlu_pfls_rt;
    }

    public String getPchs_avg_pric() {
      return pchs_avg_pric;
    }

    public String getOvrs_cblc_qty() {
      return ovrs_cblc_qty;
    }

    public String getNow_pric2() {
      return now_pric2;
    }

    public String getTr_crcy_cd() {
      return tr_crcy_cd;
    }

    public String getOvrs_excg_cd() {
      return ovrs_excg_cd;
    }

    public String getLoan_type_cd() {
      return loan_type_cd;
    }

    public String getLoan_dt() {
      return loan_dt;
    }

    public String getExpd_dt() {
      return expd_dt;
    }

    @Override
    public String toString() {
      return "ResBodyOutput1 [cano=" + cano + ", acnt_prdt_cd=" + acnt_prdt_cd + ", prdt_type_cd=" + prdt_type_cd
          + ", ovrs_pdno=" + ovrs_pdno + ", ovrs_item_name=" + ovrs_item_name + ", frcr_evlu_pfls_amt="
          + frcr_evlu_pfls_amt + ", evlu_pfls_rt=" + evlu_pfls_rt + ", pchs_avg_pric=" + pchs_avg_pric
          + ", ovrs_cblc_qty=" + ovrs_cblc_qty + ", now_pric2=" + now_pric2 + ", tr_crcy_cd=" + tr_crcy_cd
          + ", ovrs_excg_cd=" + ovrs_excg_cd + ", loan_type_cd=" + loan_type_cd + ", loan_dt=" + loan_dt + ", expd_dt="
          + expd_dt + "]";
    }

    
  }

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

    public ResBodyOutput2(String frcr_pchs_amt1, String ovrs_rlzt_pfls_amt, String ovrs_tot_pfls,
        String rlzt_erng_rt, String tot_evlu_pfls_amt, String tot_pftrt, String frcr_buy_amt_smtl1,
        String ovrs_rlzt_pfls_amt2, String frcr_buy_amt_smtl2) {
      this.frcr_pchs_amt1 = frcr_pchs_amt1;
      this.ovrs_rlzt_pfls_amt = ovrs_rlzt_pfls_amt;
      this.ovrs_tot_pfls = ovrs_tot_pfls;
      this.rlzt_erng_rt = rlzt_erng_rt;
      this.tot_evlu_pfls_amt = tot_evlu_pfls_amt;
      this.tot_pftrt = tot_pftrt;
      this.frcr_buy_amt_smtl1 = frcr_buy_amt_smtl1;
      this.ovrs_rlzt_pfls_amt2 = ovrs_rlzt_pfls_amt2;
      this.frcr_buy_amt_smtl2 = frcr_buy_amt_smtl2;
    }

    public String getFrcr_pchs_amt1() {
      return frcr_pchs_amt1;
    }

    public String getOvrs_rlzt_pfls_amt() {
      return ovrs_rlzt_pfls_amt;
    }

    public String getOvrs_tot_pfls() {
      return ovrs_tot_pfls;
    }

    public String getRlzt_erng_rt() {
      return rlzt_erng_rt;
    }

    public String getTot_evlu_pfls_amt() {
      return tot_evlu_pfls_amt;
    }

    public String getTot_pftrt() {
      return tot_pftrt;
    }

    public String getFrcr_buy_amt_smtl1() {
      return frcr_buy_amt_smtl1;
    }

    public String getOvrs_rlzt_pfls_amt2() {
      return ovrs_rlzt_pfls_amt2;
    }

    public String getFrcr_buy_amt_smtl2() {
      return frcr_buy_amt_smtl2;
    }

    @Override
    public String toString() {
      return "ResBodyOutput2 [frcr_pchs_amt1=" + frcr_pchs_amt1 + ", ovrs_rlzt_pfls_amt=" + ovrs_rlzt_pfls_amt
          + ", ovrs_tot_pfls=" + ovrs_tot_pfls + ", rlzt_erng_rt=" + rlzt_erng_rt + ", tot_evlu_pfls_amt="
          + tot_evlu_pfls_amt + ", tot_pftrt=" + tot_pftrt + ", frcr_buy_amt_smtl1=" + frcr_buy_amt_smtl1
          + ", ovrs_rlzt_pfls_amt2=" + ovrs_rlzt_pfls_amt2 + ", frcr_buy_amt_smtl2=" + frcr_buy_amt_smtl2 + "]";
    }

    
  }
}
