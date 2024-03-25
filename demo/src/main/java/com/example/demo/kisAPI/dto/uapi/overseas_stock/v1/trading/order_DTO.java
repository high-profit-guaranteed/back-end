package com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

public class order_DTO {
  public static class ReqHeader {
    private final String content_type;
    @NonNull
    private final String authorization;
    @NonNull
    private final String appkey;
    @NonNull
    private final String appsecret;
    // @NonNull
    // private final String personalseckey;
    @NonNull
    private final String tr_id;
    private final String tr_cont;
    // private final String custtype;
    // private final String seq_no;
    // private final String mac_address;
    // private final String phone_number;
    // private final String ip_addr;
    // private final String hashkey;
    // private final String gt_uid;

    private ReqHeader(@NonNull String content_type, @NonNull String authorization, @NonNull String appkey,
        @NonNull String appsecret, @NonNull String tr_id, String tr_cont) {
      this.content_type = content_type;
      this.authorization = authorization;
      this.appkey = appkey;
      this.appsecret = appsecret;
      this.tr_id = tr_id;
      this.tr_cont = tr_cont;
    }

    @NonNull
    public static ReqHeader from(@NonNull Account account, boolean isBuy, String tr_cont) {
      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      String AUTHORIZATION = account.getAccessToken();
      String tr_id = account.isVirtual() ? (isBuy?"VTTT1002U":"VTTT1001U") : (isBuy?"TTTT1002U":"TTTT1006U");
      if (APP_KEY == null || APP_SECRET == null || AUTHORIZATION == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      return new ReqHeader("application/json; charset=utf-8", AUTHORIZATION, APP_KEY, APP_SECRET, tr_id, tr_cont);
    }

    @NonNull
    public static ReqHeader from(@NonNull Account account, boolean isBuy) {
      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      String AUTHORIZATION = account.getAccessToken();
      String tr_id = account.isVirtual() ? (isBuy?"VTTT1002U":"VTTT1001U") : (isBuy?"TTTT1002U":"TTTT1006U");
      if (APP_KEY == null || APP_SECRET == null || AUTHORIZATION == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      return new ReqHeader("application/json; charset=utf-8", AUTHORIZATION, APP_KEY, APP_SECRET, tr_id, null);
    }

    public String getContent_type() {
      return content_type;
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

    public String getTr_cont() {
      return tr_cont;
    }
  }

  public static class ReqBody {
    @NonNull
    private final String CANO;
    @NonNull
    private final String ACNT_PRDT_CD;
    @NonNull
    private final String OVRS_EXCG_CD;
    @NonNull
    private final String PDNO;
    @NonNull
    private final String ORD_QTY;
    @NonNull
    private final String OVRS_ORD_UNPR;
    private final String CTAC_TLNO;
    private final String MGCO_APTM_ODNO;
    private final String SLL_TYPE;
    @NonNull
    private final String ORD_SVR_DVSN_CD;
    @NonNull
    private final String ORD_DVSN;

    private ReqBody(@NonNull String CANO, @NonNull String ACNT_PRDT_CD, @NonNull String OVRS_EXCG_CD, @NonNull String PDNO,
        @NonNull String ORD_QTY, @NonNull String OVRS_ORD_UNPR, String CTAC_TLNO, String MGCO_APTM_ODNO, String SLL_TYPE,
        @NonNull String ORD_SVR_DVSN_CD, @NonNull String ORD_DVSN) {
      this.CANO = CANO;
      this.ACNT_PRDT_CD = ACNT_PRDT_CD;
      this.OVRS_EXCG_CD = OVRS_EXCG_CD;
      this.PDNO = PDNO;
      this.ORD_QTY = ORD_QTY;
      this.OVRS_ORD_UNPR = OVRS_ORD_UNPR;
      this.CTAC_TLNO = CTAC_TLNO;
      this.MGCO_APTM_ODNO = MGCO_APTM_ODNO;
      this.SLL_TYPE = SLL_TYPE;
      this.ORD_SVR_DVSN_CD = ORD_SVR_DVSN_CD;
      this.ORD_DVSN = ORD_DVSN;
    }

    // @NonNull
    // public static ReqBody from(@NonNull Account account, @NonNull String PDNO, @NonNull String ORD_QTY, String OVRS_ORD_UNPR,
    //     String CTAC_TLNO, String MGCO_APTM_ODNO, String SLL_TYPE, @NonNull String ORD_SVR_DVSN_CD, @NonNull String ORD_DVSN) {
    //   String APP_KEY = account.getAPP_KEY();
    //   String APP_SECRET = account.getAPP_SECRET();
    //   String CANO = String.valueOf(account.getAccountNumber());
    //   String ACNT_PRDT_CD = String.valueOf(account.getAccountProdCode());
    //   if (APP_KEY == null || APP_SECRET == null || CANO == null || ACNT_PRDT_CD == null) {
    //     throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
    //   }
    //   if (ACNT_PRDT_CD.length() == 1) {
    //     ACNT_PRDT_CD = "0" + ACNT_PRDT_CD;
    //   } else if (ACNT_PRDT_CD.length() != 2) {
    //     throw new IllegalStateException("계좌 상품 코드가 올바르지 않습니다.");
    //   }
    //   if (OVRS_ORD_UNPR == null) {
    //     OVRS_ORD_UNPR = "0";
    //   }

    //   return new ReqBody(CANO, ACNT_PRDT_CD, "NASD", PDNO, ORD_QTY, OVRS_ORD_UNPR, CTAC_TLNO, MGCO_APTM_ODNO,
    //       SLL_TYPE, ORD_SVR_DVSN_CD, ORD_DVSN);
    // }

    // 지정가
    @NonNull
    public static ReqBody from(@NonNull Account account, @NonNull String code, @NonNull String amount, @NonNull String price, boolean isBuy) {
      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      String CANO = String.valueOf(account.getAccountNumber());
      String ACNT_PRDT_CD = String.valueOf(account.getAccountProdCode());
      if (APP_KEY == null || APP_SECRET == null || CANO == null || ACNT_PRDT_CD == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }
      if (ACNT_PRDT_CD.length() == 1) {
        ACNT_PRDT_CD = "0" + ACNT_PRDT_CD;
      } else if (ACNT_PRDT_CD.length() != 2) {
        throw new IllegalStateException("계좌 상품 코드가 올바르지 않습니다.");
      }

      return new ReqBody(CANO, ACNT_PRDT_CD, "NASD", code, amount, price, null, null, isBuy?null:"00", "0",
          "00");
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

    public String getPDNO() {
      return PDNO;
    }

    public String getORD_QTY() {
      return ORD_QTY;
    }

    public String getOVRS_ORD_UNPR() {
      return OVRS_ORD_UNPR;
    }

    public String getCTAC_TLNO() {
      return CTAC_TLNO;
    }

    public String getMGCO_APTM_ODNO() {
      return MGCO_APTM_ODNO;
    }

    public String getSLL_TYPE() {
      return SLL_TYPE;
    }

    public String getORD_SVR_DVSN_CD() {
      return ORD_SVR_DVSN_CD;
    }

    public String getORD_DVSN() {
      return ORD_DVSN;
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

    @Override
    public String toString() {
      return "ResHeader [content_type=" + content_type + ", tr_id=" + tr_id + ", tr_cont=" + tr_cont + ", gt_uid="
          + gt_uid + "]";
    }
    
  }

  public static class ResBody {
    private final String rt_cd;
    private final String msg_cd;
    private final String msg1;
    private final ResBodyOuput output;

    public ResBody(String rt_cd, String msg_cd, String msg1, ResBodyOuput output) {
      this.rt_cd = rt_cd;
      this.msg_cd = msg_cd;
      this.msg1 = msg1;
      this.output = output;
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

    public ResBodyOuput getOutput() {
      return output;
    }

    @Override
    public String toString() {
      return "ResBody [rt_cd=" + rt_cd + ", msg_cd=" + msg_cd + ", msg1=" + msg1 + ", output=" + output + "]";
    }
  }

  public static class ResBodyOuput {
    private final String KRX_FWDG_ORD_ORGNO;
    private final String ODNO;
    private final String ORD_TMD;

    public ResBodyOuput(String KRX_FWDG_ORD_ORGNO, String ODNO, String ORD_TMD) {
      this.KRX_FWDG_ORD_ORGNO = KRX_FWDG_ORD_ORGNO;
      this.ODNO = ODNO;
      this.ORD_TMD = ORD_TMD;
    }

    public String getKRX_FWDG_ORD_ORGNO() {
      return KRX_FWDG_ORD_ORGNO;
    }

    public String getODNO() {
      return ODNO;
    }

    public String getORD_TMD() {
      return ORD_TMD;
    }

    @Override
    public String toString() {
      return "ResBodyOuput [KRX_FWDG_ORD_ORGNO=" + KRX_FWDG_ORD_ORGNO + ", ODNO=" + ODNO + ", ORD_TMD=" + ORD_TMD + "]";
    }
  }
}
