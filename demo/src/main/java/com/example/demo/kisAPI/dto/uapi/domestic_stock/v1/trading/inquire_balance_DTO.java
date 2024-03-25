package com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading;

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
        tr_id = "VTTC8434R";
      } else {
        tr_id = "TTTC8434R";
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

  /*
CANO	종합계좌번호	String	Y	8	계좌번호 체계(8-2)의 앞 8자리
ACNT_PRDT_CD	계좌상품코드	String	Y	2	계좌번호 체계(8-2)의 뒤 2자리
AFHR_FLPR_YN	시간외단일가여부	String	Y	1	N : 기본값
Y : 시간외단일가
OFL_YN	오프라인여부	String	Y	1	공란(Default)
INQR_DVSN	조회구분	String	Y	2	01 : 대출일별
02 : 종목별
UNPR_DVSN	단가구분	String	Y	2	01 : 기본값
FUND_STTL_ICLD_YN	펀드결제분포함여부	String	Y	1	N : 포함하지 않음
Y : 포함
FNCG_AMT_AUTO_RDPT_YN	융자금액자동상환여부	String	Y	1	N : 기본값
PRCS_DVSN	처리구분	String	Y	2	00 : 전일매매포함
01 : 전일매매미포함
CTX_AREA_FK100	연속조회검색조건100	String	Y	100	공란 : 최초 조회시
이전 조회 Output CTX_AREA_FK100 값 : 다음페이지 조회시(2번째부터)
CTX_AREA_NK100	연속조회키100	String	Y	100	공란 : 최초 조회시
이전 조회 Output CTX_AREA_NK100 값 : 다음페이지 조회시(2번째부터)
   */
  public static class ReqQueryParam {
    @NonNull
    private final String CANO;
    @NonNull
    private final String ACNT_PRDT_CD;
    @NonNull
    private final String AFHR_FLPR_YN;
    @NonNull
    private final String OFL_YN;
    @NonNull
    private final String INQR_DVSN;
    @NonNull
    private final String UNPR_DVSN;
    @NonNull
    private final String FUND_STTL_ICLD_YN;
    @NonNull
    private final String FNCG_AMT_AUTO_RDPT_YN;
    @NonNull
    private final String PRCS_DVSN;
    @Nullable
    private final String CTX_AREA_FK100;
    @Nullable
    private final String CTX_AREA_NK100;

    

    public ReqQueryParam(@NonNull String CANO, @NonNull String ACNT_PRDT_CD, @NonNull String AFHR_FLPR_YN, @NonNull String OFL_YN, @NonNull String INQR_DVSN,
    @NonNull String UNPR_DVSN, @NonNull String FUND_STTL_ICLD_YN, @NonNull String FNCG_AMT_AUTO_RDPT_YN, @NonNull String PRCS_DVSN,
    String CTX_AREA_FK100, String CTX_AREA_NK100) {
      this.CANO = CANO;
      this.ACNT_PRDT_CD = ACNT_PRDT_CD;
      this.AFHR_FLPR_YN = AFHR_FLPR_YN;
      this.OFL_YN = OFL_YN;
      this.INQR_DVSN = INQR_DVSN;
      this.UNPR_DVSN = UNPR_DVSN;
      this.FUND_STTL_ICLD_YN = FUND_STTL_ICLD_YN;
      this.FNCG_AMT_AUTO_RDPT_YN = FNCG_AMT_AUTO_RDPT_YN;
      this.PRCS_DVSN = PRCS_DVSN;
      this.CTX_AREA_FK100 = CTX_AREA_FK100;
      this.CTX_AREA_NK100 = CTX_AREA_NK100;
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
      return new ReqQueryParam(CANO, ACNT_PRDT_CD, "N", "", "01", "01", "N", "N", "00", "", "");
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
      return new ReqQueryParam(CANO, ACNT_PRDT_CD, "N", "", "01", "01", "N", "N", "00", CTX_AREA_FK200, CTX_AREA_NK200);
    }

    public String getCANO() {
      return CANO;
    }

    public String getACNT_PRDT_CD() {
      return ACNT_PRDT_CD;
    }

    public String getAFHR_FLPR_YN() {
      return AFHR_FLPR_YN;
    }

    public String getOFL_YN() {
      return OFL_YN;
    }

    public String getINQR_DVSN() {
      return INQR_DVSN;
    }

    public String getUNPR_DVSN() {
      return UNPR_DVSN;
    }

    public String getFUND_STTL_ICLD_YN() {
      return FUND_STTL_ICLD_YN;
    }

    public String getFNCG_AMT_AUTO_RDPT_YN() {
      return FNCG_AMT_AUTO_RDPT_YN;
    }

    public String getPRCS_DVSN() {
      return PRCS_DVSN;
    }

    public String getCTX_AREA_FK100() {
      return CTX_AREA_FK100;
    }

    public String getCTX_AREA_NK100() {
      return CTX_AREA_NK100;
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
    private final List<ResBodyOutput2> output2;

    public ResBody(String rt_cd, String msg_cd, String msg1, String ctx_area_fk200, String ctx_area_nk200,
        List<ResBodyOutput1> output1, List<ResBodyOutput2> output2) {
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

    public List<ResBodyOutput2> getOutput2() {
      return output2;
    }

    @Override
    public String toString() {
      return "ResBody [rt_cd=" + rt_cd + ", msg_cd=" + msg_cd + ", msg1=" + msg1 + ", ctx_area_fk200=" + ctx_area_fk200
          + ", ctx_area_nk200=" + ctx_area_nk200 + ", output1=" + output1 + ", output2=" + output2 + "]";
    }
  }

  /*
-pdno	상품번호	String	Y	12	종목번호(뒷 6자리)
-prdt_name	상품명	String	Y	60	종목명
-trad_dvsn_name	매매구분명	String	Y	60	매수매도구분
-bfdy_buy_qty	전일매수수량	String	Y	10	
-bfdy_sll_qty	전일매도수량	String	Y	10	
-thdt_buyqty	금일매수수량	String	Y	10	
-thdt_sll_qty	금일매도수량	String	Y	10	
-hldg_qty	보유수량	String	Y	19	
-ord_psbl_qty	주문가능수량	String	Y	10	
-pchs_avg_pric	매입평균가격	String	Y	22	매입금액 / 보유수량
-pchs_amt	매입금액	String	Y	19	
-prpr	현재가	String	Y	19	
-evlu_amt	평가금액	String	Y	19	
-evlu_pfls_amt	평가손익금액	String	Y	19	평가금액 - 매입금액
-evlu_pfls_rt	평가손익율	String	Y	9	
-evlu_erng_rt	평가수익율	String	Y	31	미사용항목(0으로 출력)
-loan_dt	대출일자	String	Y	8	INQR_DVSN(조회구분)을 01(대출일별)로 설정해야 값이 나옴
-loan_amt	대출금액	String	Y	19	
-stln_slng_chgs	대주매각대금	String	Y	19	
-expd_dt	만기일자	String	Y	8	
-fltt_rt	등락율	String	Y	31	
-bfdy_cprs_icdc	전일대비증감	String	Y	19	
-item_mgna_rt_name	종목증거금율명	String	Y	20	
-grta_rt_name	보증금율명	String	Y	20	
-sbst_pric	대용가격	String	Y	19	증권매매의 위탁보증금으로서 현금 대신에 사용되는 유가증권 가격
-stck_loan_unpr	주식대출단가	String	Y	22	
   */

  public static class ResBodyOutput1 {
    private final String pdno;
    private final String prdt_name;
    private final String trad_dvsn_name;
    private final String bfdy_buy_qty;
    private final String bfdy_sll_qty;
    private final String thdt_buyqty;
    private final String thdt_sll_qty;
    private final String hldg_qty;
    private final String ord_psbl_qty;
    private final String pchs_avg_pric;
    private final String pchs_amt;
    private final String prpr;
    private final String evlu_amt;
    private final String evlu_pfls_amt;
    private final String evlu_pfls_rt;
    private final String evlu_erng_rt;
    private final String loan_dt;
    private final String loan_amt;
    private final String stln_slng_chgs;
    private final String expd_dt;
    private final String fltt_rt;
    private final String bfdy_cprs_icdc;
    private final String item_mgna_rt_name;
    private final String grta_rt_name;
    private final String sbst_pric;
    private final String stck_loan_unpr;
    
    public ResBodyOutput1(String pdno, String prdt_name, String trad_dvsn_name, String bfdy_buy_qty,
        String bfdy_sll_qty, String thdt_buyqty, String thdt_sll_qty, String hldg_qty, String ord_psbl_qty,
        String pchs_avg_pric, String pchs_amt, String prpr, String evlu_amt, String evlu_pfls_amt, String evlu_pfls_rt,
        String evlu_erng_rt, String loan_dt, String loan_amt, String stln_slng_chgs, String expd_dt, String fltt_rt,
        String bfdy_cprs_icdc, String item_mgna_rt_name, String grta_rt_name, String sbst_pric, String stck_loan_unpr) {
      this.pdno = pdno;
      this.prdt_name = prdt_name;
      this.trad_dvsn_name = trad_dvsn_name;
      this.bfdy_buy_qty = bfdy_buy_qty;
      this.bfdy_sll_qty = bfdy_sll_qty;
      this.thdt_buyqty = thdt_buyqty;
      this.thdt_sll_qty = thdt_sll_qty;
      this.hldg_qty = hldg_qty;
      this.ord_psbl_qty = ord_psbl_qty;
      this.pchs_avg_pric = pchs_avg_pric;
      this.pchs_amt = pchs_amt;
      this.prpr = prpr;
      this.evlu_amt = evlu_amt;
      this.evlu_pfls_amt = evlu_pfls_amt;
      this.evlu_pfls_rt = evlu_pfls_rt;
      this.evlu_erng_rt = evlu_erng_rt;
      this.loan_dt = loan_dt;
      this.loan_amt = loan_amt;
      this.stln_slng_chgs = stln_slng_chgs;
      this.expd_dt = expd_dt;
      this.fltt_rt = fltt_rt;
      this.bfdy_cprs_icdc = bfdy_cprs_icdc;
      this.item_mgna_rt_name = item_mgna_rt_name;
      this.grta_rt_name = grta_rt_name;
      this.sbst_pric = sbst_pric;
      this.stck_loan_unpr = stck_loan_unpr;
    }

    public String getPdno() {
      return pdno;
    }
    public String getPrdt_name() {
      return prdt_name;
    }
    public String getTrad_dvsn_name() {
      return trad_dvsn_name;
    }
    public String getBfdy_buy_qty() {
      return bfdy_buy_qty;
    }
    public String getBfdy_sll_qty() {
      return bfdy_sll_qty;
    }
    public String getThdt_buyqty() {
      return thdt_buyqty;
    }
    public String getThdt_sll_qty() {
      return thdt_sll_qty;
    }
    public String getHldg_qty() {
      return hldg_qty;
    }
    public String getOrd_psbl_qty() {
      return ord_psbl_qty;
    }
    public String getPchs_avg_pric() {
      return pchs_avg_pric;
    }
    public String getPchs_amt() {
      return pchs_amt;
    }
    public String getPrpr() {
      return prpr;
    }
    public String getEvlu_amt() {
      return evlu_amt;
    }
    public String getEvlu_pfls_amt() {
      return evlu_pfls_amt;
    }
    public String getEvlu_pfls_rt() {
      return evlu_pfls_rt;
    }
    public String getEvlu_erng_rt() {
      return evlu_erng_rt;
    }
    public String getLoan_dt() {
      return loan_dt;
    }
    public String getLoan_amt() {
      return loan_amt;
    }
    public String getStln_slng_chgs() {
      return stln_slng_chgs;
    }
    public String getExpd_dt() {
      return expd_dt;
    }
    public String getFltt_rt() {
      return fltt_rt;
    }
    public String getBfdy_cprs_icdc() {
      return bfdy_cprs_icdc;
    }
    public String getItem_mgna_rt_name() {
      return item_mgna_rt_name;
    }
    public String getGrta_rt_name() {
      return grta_rt_name;
    }
    public String getSbst_pric() {
      return sbst_pric;
    }
    public String getStck_loan_unpr() {
      return stck_loan_unpr;
    }

    @Override
    public String toString() {
      return "ResBodyOutput1 [pdno=" + pdno + ", prdt_name=" + prdt_name + ", trad_dvsn_name=" + trad_dvsn_name
          + ", bfdy_buy_qty=" + bfdy_buy_qty + ", bfdy_sll_qty=" + bfdy_sll_qty + ", thdt_buyqty=" + thdt_buyqty
          + ", thdt_sll_qty=" + thdt_sll_qty + ", hldg_qty=" + hldg_qty + ", ord_psbl_qty=" + ord_psbl_qty
          + ", pchs_avg_pric=" + pchs_avg_pric + ", pchs_amt=" + pchs_amt + ", prpr=" + prpr + ", evlu_amt=" + evlu_amt
          + ", evlu_pfls_amt=" + evlu_pfls_amt + ", evlu_pfls_rt=" + evlu_pfls_rt + ", evlu_erng_rt=" + evlu_erng_rt
          + ", loan_dt=" + loan_dt + ", loan_amt=" + loan_amt + ", stln_slng_chgs=" + stln_slng_chgs + ", expd_dt="
          + expd_dt + ", fltt_rt=" + fltt_rt + ", bfdy_cprs_icdc=" + bfdy_cprs_icdc + ", item_mgna_rt_name="
          + item_mgna_rt_name + ", grta_rt_name=" + grta_rt_name + ", sbst_pric=" + sbst_pric + ", stck_loan_unpr="
          + stck_loan_unpr + "]";
    }
  }

  /*
-dnca_tot_amt	예수금총금액	String	Y	19	예수금
-nxdy_excc_amt	익일정산금액	String	Y	19	D+1 예수금
-prvs_rcdl_excc_amt	가수도정산금액	String	Y	19	D+2 예수금
-cma_evlu_amt	CMA평가금액	String	Y	19	
-bfdy_buy_amt	전일매수금액	String	Y	19	
-thdt_buy_amt	금일매수금액	String	Y	19	
-nxdy_auto_rdpt_amt	익일자동상환금액	String	Y	19	
-bfdy_sll_amt	전일매도금액	String	Y	19	
-thdt_sll_amt	금일매도금액	String	Y	19	
-d2_auto_rdpt_amt	D+2자동상환금액	String	Y	19	
-bfdy_tlex_amt	전일제비용금액	String	Y	19	
-thdt_tlex_amt	금일제비용금액	String	Y	19	
-tot_loan_amt	총대출금액	String	Y	19	
-scts_evlu_amt	유가평가금액	String	Y	19	
-tot_evlu_amt	총평가금액	String	Y	19	유가증권 평가금액 합계금액 + D+2 예수금
-nass_amt	순자산금액	String	Y	19	
-fncg_gld_auto_rdpt_yn	융자금자동상환여부	String	Y	1	보유현금에 대한 융자금만 차감여부
신용융자 매수체결 시점에서는 융자비율을 매매대금 100%로 계산 하였다가 수도결제일에 보증금에 해당하는 금액을 고객의 현금으로 충당하여 융자금을 감소시키는 업무
-pchs_amt_smtl_amt	매입금액합계금액	String	Y	19	
-evlu_amt_smtl_amt	평가금액합계금액	String	Y	19	유가증권 평가금액 합계금액
-evlu_pfls_smtl_amt	평가손익합계금액	String	Y	19	
-tot_stln_slng_chgs	총대주매각대금	String	Y	19	
-bfdy_tot_asst_evlu_amt	전일총자산평가금액	String	Y	19	
-asst_icdc_amt	자산증감액	String	Y	19	
-asst_icdc_erng_rt	자산증감수익율	String	Y	31	데이터 미제공
   */
  public static class ResBodyOutput2 {
    private final String dnca_tot_amt;
    private final String nxdy_excc_amt;
    private final String prvs_rcdl_excc_amt;
    private final String cma_evlu_amt;
    private final String bfdy_buy_amt;
    private final String thdt_buy_amt;
    private final String nxdy_auto_rdpt_amt;
    private final String bfdy_sll_amt;
    private final String thdt_sll_amt;
    private final String d2_auto_rdpt_amt;
    private final String bfdy_tlex_amt;
    private final String thdt_tlex_amt;
    private final String tot_loan_amt;
    private final String scts_evlu_amt;
    private final String tot_evlu_amt;
    private final String nass_amt;
    private final String fncg_gld_auto_rdpt_yn;
    private final String pchs_amt_smtl_amt;
    private final String evlu_amt_smtl_amt;
    private final String evlu_pfls_smtl_amt;
    private final String tot_stln_slng_chgs;
    private final String bfdy_tot_asst_evlu_amt;
    private final String asst_icdc_amt;
    private final String asst_icdc_erng_rt;

    public ResBodyOutput2(String dnca_tot_amt, String nxdy_excc_amt, String prvs_rcdl_excc_amt, String cma_evlu_amt,
        String bfdy_buy_amt, String thdt_buy_amt, String nxdy_auto_rdpt_amt, String bfdy_sll_amt, String thdt_sll_amt,
        String d2_auto_rdpt_amt, String bfdy_tlex_amt, String thdt_tlex_amt, String tot_loan_amt, String scts_evlu_amt,
        String tot_evlu_amt, String nass_amt, String fncg_gld_auto_rdpt_yn, String pchs_amt_smtl_amt,
        String evlu_amt_smtl_amt, String evlu_pfls_smtl_amt, String tot_stln_slng_chgs, String bfdy_tot_asst_evlu_amt,
        String asst_icdc_amt, String asst_icdc_erng_rt) {
      this.dnca_tot_amt = dnca_tot_amt;
      this.nxdy_excc_amt = nxdy_excc_amt;
      this.prvs_rcdl_excc_amt = prvs_rcdl_excc_amt;
      this.cma_evlu_amt = cma_evlu_amt;
      this.bfdy_buy_amt = bfdy_buy_amt;
      this.thdt_buy_amt = thdt_buy_amt;
      this.nxdy_auto_rdpt_amt = nxdy_auto_rdpt_amt;
      this.bfdy_sll_amt = bfdy_sll_amt;
      this.thdt_sll_amt = thdt_sll_amt;
      this.d2_auto_rdpt_amt = d2_auto_rdpt_amt;
      this.bfdy_tlex_amt = bfdy_tlex_amt;
      this.thdt_tlex_amt = thdt_tlex_amt;
      this.tot_loan_amt = tot_loan_amt;
      this.scts_evlu_amt = scts_evlu_amt;
      this.tot_evlu_amt = tot_evlu_amt;
      this.nass_amt = nass_amt;
      this.fncg_gld_auto_rdpt_yn = fncg_gld_auto_rdpt_yn;
      this.pchs_amt_smtl_amt = pchs_amt_smtl_amt;
      this.evlu_amt_smtl_amt = evlu_amt_smtl_amt;
      this.evlu_pfls_smtl_amt = evlu_pfls_smtl_amt;
      this.tot_stln_slng_chgs = tot_stln_slng_chgs;
      this.bfdy_tot_asst_evlu_amt = bfdy_tot_asst_evlu_amt;
      this.asst_icdc_amt = asst_icdc_amt;
      this.asst_icdc_erng_rt = asst_icdc_erng_rt;
    }

    public String getDnca_tot_amt() {
      return dnca_tot_amt;
    }

    public String getNxdy_excc_amt() {
      return nxdy_excc_amt;
    }

    public String getPrvs_rcdl_excc_amt() {
      return prvs_rcdl_excc_amt;
    }

    public String getCma_evlu_amt() {
      return cma_evlu_amt;
    }

    public String getBfdy_buy_amt() {
      return bfdy_buy_amt;
    }

    public String getThdt_buy_amt() {
      return thdt_buy_amt;
    }

    public String getNxdy_auto_rdpt_amt() {
      return nxdy_auto_rdpt_amt;
    }

    public String getBfdy_sll_amt() {
      return bfdy_sll_amt;
    }

    public String getThdt_sll_amt() {
      return thdt_sll_amt;
    }

    public String getD2_auto_rdpt_amt() {
      return d2_auto_rdpt_amt;
    }

    public String getBfdy_tlex_amt() {
      return bfdy_tlex_amt;
    }

    public String getThdt_tlex_amt() {
      return thdt_tlex_amt;
    }

    public String getTot_loan_amt() {
      return tot_loan_amt;
    }

    public String getScts_evlu_amt() {
      return scts_evlu_amt;
    }

    public String getTot_evlu_amt() {
      return tot_evlu_amt;
    }

    public String getNass_amt() {
      return nass_amt;
    }

    public String getFncg_gld_auto_rdpt_yn() {
      return fncg_gld_auto_rdpt_yn;
    }

    public String getPchs_amt_smtl_amt() {
      return pchs_amt_smtl_amt;
    }

    public String getEvlu_amt_smtl_amt() {
      return evlu_amt_smtl_amt;
    }

    public String getEvlu_pfls_smtl_amt() {
      return evlu_pfls_smtl_amt;
    }

    public String getTot_stln_slng_chgs() {
      return tot_stln_slng_chgs;
    }

    public String getBfdy_tot_asst_evlu_amt() {
      return bfdy_tot_asst_evlu_amt;
    }

    public String getAsst_icdc_amt() {
      return asst_icdc_amt;
    }

    public String getAsst_icdc_erng_rt() {
      return asst_icdc_erng_rt;
    }

    @Override
    public String toString() {
      return "ResBodyOutput2 [dnca_tot_amt=" + dnca_tot_amt + ", nxdy_excc_amt=" + nxdy_excc_amt
          + ", prvs_rcdl_excc_amt=" + prvs_rcdl_excc_amt + ", cma_evlu_amt=" + cma_evlu_amt + ", bfdy_buy_amt="
          + bfdy_buy_amt + ", thdt_buy_amt=" + thdt_buy_amt + ", nxdy_auto_rdpt_amt=" + nxdy_auto_rdpt_amt
          + ", bfdy_sll_amt=" + bfdy_sll_amt + ", thdt_sll_amt=" + thdt_sll_amt + ", d2_auto_rdpt_amt="
          + d2_auto_rdpt_amt + ", bfdy_tlex_amt=" + bfdy_tlex_amt + ", thdt_tlex_amt=" + thdt_tlex_amt
          + ", tot_loan_amt=" + tot_loan_amt + ", scts_evlu_amt=" + scts_evlu_amt + ", tot_evlu_amt=" + tot_evlu_amt
          + ", nass_amt=" + nass_amt + ", fncg_gld_auto_rdpt_yn=" + fncg_gld_auto_rdpt_yn + ", pchs_amt_smtl_amt="
          + pchs_amt_smtl_amt + ", evlu_amt_smtl_amt=" + evlu_amt_smtl_amt + ", evlu_pfls_smtl_amt="
          + evlu_pfls_smtl_amt + ", tot_stln_slng_chgs=" + tot_stln_slng_chgs + ", bfdy_tot_asst_evlu_amt="
          + bfdy_tot_asst_evlu_amt + ", asst_icdc_amt=" + asst_icdc_amt + ", asst_icdc_erng_rt=" + asst_icdc_erng_rt
          + "]";
    }
  }
}
