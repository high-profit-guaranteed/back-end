package com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading;

import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

public class order_cash_DTO {
  /*
content-type	컨텐츠타입	String	N	40	application/json; charset=utf-8
authorization	접근토큰	String	Y	40	OAuth 토큰이 필요한 API 경우 발급한 Access token
일반고객(Access token 유효기간 1일, OAuth 2.0의 Client Credentials Grant 절차를 준용)
법인(Access token 유효기간 3개월, Refresh token 유효기간 1년, OAuth 2.0의 Authorization Code Grant 절차를 준용)

※ 토큰 지정시 토큰 타입("Bearer") 지정 필요. 즉, 발급받은 접근토큰 앞에 앞에 "Bearer" 붙여서 호출
EX) "Bearer eyJ..........8GA"
appkey	앱키	String	Y	36	한국투자증권 홈페이지에서 발급받은 appkey (절대 노출되지 않도록 주의해주세요.)
appsecret	앱시크릿키	String	Y	180	한국투자증권 홈페이지에서 발급받은 appsecret (절대 노출되지 않도록 주의해주세요.)
personalseckey	고객식별키	String	N	180	[법인 필수] 제휴사 회원 관리를 위한 고객식별키
tr_id	거래ID	String	Y	13	[실전투자]
TTTC0802U : 주식 현금 매수 주문
TTTC0801U : 주식 현금 매도 주문

[모의투자]
VTTC0802U : 주식 현금 매수 주문
VTTC0801U : 주식 현금 매도 주문
tr_cont	연속 거래 여부	String	N	1	공백 : 초기 조회
N : 다음 데이터 조회 (output header의 tr_cont가 M일 경우)
custtype	고객타입	String	N	1	B : 법인
P : 개인
seq_no	일련번호	String	N	2	[법인 필수] 001
mac_address	맥주소	String	N	12	법인고객 혹은 개인고객의 Mac address 값
phone_number	핸드폰번호	String	N	12	[법인 필수] 제휴사APP을 사용하는 경우 사용자(회원) 핸드폰번호
ex) 01011112222 (하이픈 등 구분값 제거)
ip_addr	접속 단말 공인 IP	String	N	12	[법인 필수] 사용자(회원)의 IP Address
hashkey	해쉬키	String	N	256	※ 입력 불필요
gt_uid	Global UID	String	N	32	[법인 필수] 거래고유번호로 사용하므로 거래별로 UNIQUE해야 함
 */
  public static class ReqHeader {
    @NonNull
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
      this.authorization = "Bearer " + authorization;
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
      String tr_id = account.isVirtual() ? (isBuy?"VTTC0802U":"VTTC0801U") : (isBuy?"TTTC0802U":"TTTC0801U");
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
      String tr_id = account.isVirtual() ? (isBuy?"VTTC0802U":"VTTC0801U") : (isBuy?"TTTC0802U":"TTTC0801U");
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

  /*
CANO	종합계좌번호	String	Y	8	계좌번호 체계(8-2)의 앞 8자리
ACNT_PRDT_CD	계좌상품코드	String	Y	2	계좌번호 체계(8-2)의 뒤 2자리
PDNO	종목코드(6자리)	String	Y	12	종목코드(6자리)
ETN의 경우, Q로 시작 (EX. Q500001)
ORD_DVSN	주문구분	String	Y	2	00 : 지정가
01 : 시장가
02 : 조건부지정가
03 : 최유리지정가
04 : 최우선지정가
05 : 장전 시간외 (08:20~08:40)
06 : 장후 시간외 (15:30~16:00)
07 : 시간외 단일가(16:00~18:00)
08 : 자기주식
09 : 자기주식S-Option
10 : 자기주식금전신탁
11 : IOC지정가 (즉시체결,잔량취소)
12 : FOK지정가 (즉시체결,전량취소)
13 : IOC시장가 (즉시체결,잔량취소)
14 : FOK시장가 (즉시체결,전량취소)
15 : IOC최유리 (즉시체결,잔량취소)
16 : FOK최유리 (즉시체결,전량취소)
ORD_QTY	주문수량	String	Y	10	주문주식수
ORD_UNPR	주문단가	String	Y	19	* 주문단가가 없는주문은 상한가로 주문금액을 선정하고 이후 체결이되면 체결금액로 정산
* 지정가 이외의 장전 시간외, 장후 시간외, 시장가 등 모든 주문구분의 경우 1주당 가격을 공란으로 비우지 않음 "0"으로 입력 권고
1주당 가격
ALGO_NO	알고리즘번호	String	N	10	미사용 */
  public static class ReqBody {
    @NonNull
    private final String CANO;
    @NonNull
    private final String ACNT_PRDT_CD;
    @NonNull
    private final String PDNO;
    @NonNull
    private final String ORD_DVSN;
    @NonNull
    private final String ORD_QTY;
    @NonNull
    private final String ORD_UNPR;
    // private final String ALGO_NO;

    public ReqBody(@NonNull String CANO, @NonNull String ACNT_PRDT_CD, @NonNull String PDNO, @NonNull String ORD_DVSN,
        @NonNull String ORD_QTY, @NonNull String ORD_UNPR) {
      this.CANO = CANO;
      this.ACNT_PRDT_CD = ACNT_PRDT_CD;
      this.PDNO = PDNO;
      this.ORD_DVSN = ORD_DVSN;
      this.ORD_QTY = ORD_QTY;
      this.ORD_UNPR = ORD_UNPR;
    }

    // 지정가
    @NonNull
    public static ReqBody from(@NonNull Account account, @NonNull String code,
        @NonNull String amount, @NonNull String price) {
      String CANO = String.valueOf(account.getAccountNumber());
      String ACNT_PRDT_CD = String.valueOf(account.getAccountProdCode());
      if (CANO == null || ACNT_PRDT_CD == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }
      if (ACNT_PRDT_CD.length() == 1) {
        ACNT_PRDT_CD = "0" + ACNT_PRDT_CD;
      } else if (ACNT_PRDT_CD.length() != 2) {
        throw new IllegalStateException("계좌 상품 코드가 올바르지 않습니다.");
      }

      return new ReqBody(CANO, ACNT_PRDT_CD, code, "00", amount, price);
    }

    // 시장가
    @NonNull
    public static ReqBody from(@NonNull Account account, @NonNull String code,
        @NonNull String amount) {
      String CANO = String.valueOf(account.getAccountNumber());
      String ACNT_PRDT_CD = String.valueOf(account.getAccountProdCode());
      if (CANO == null || ACNT_PRDT_CD == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }
      if (ACNT_PRDT_CD.length() == 1) {
        ACNT_PRDT_CD = "0" + ACNT_PRDT_CD;
      } else if (ACNT_PRDT_CD.length() != 2) {
        throw new IllegalStateException("계좌 상품 코드가 올바르지 않습니다.");
      }

      return new ReqBody(CANO, ACNT_PRDT_CD, code, "01", amount, "0");
    }

    public String getCANO() {
      return CANO;
    }

    public String getACNT_PRDT_CD() {
      return ACNT_PRDT_CD;
    }

    public String getPDNO() {
      return PDNO;
    }

    public String getORD_DVSN() {
      return ORD_DVSN;
    }

    public String getORD_QTY() {
      return ORD_QTY;
    }

    public String getORD_UNPR() {
      return ORD_UNPR;
    }

    @NonNull
    public Map<String, String> toMap() {
      Map<String, String> map = new HashMap<String, String>();
      map.put("CANO", CANO);
      map.put("ACNT_PRDT_CD", ACNT_PRDT_CD);
      map.put("PDNO", PDNO);
      map.put("ORD_DVSN", ORD_DVSN);
      map.put("ORD_QTY", ORD_QTY);
      map.put("ORD_UNPR", ORD_UNPR);
      return map;
    }
  }

/*
content-type	컨텐츠타입	String	Y	40	application/json; charset=utf-8
tr_id	거래ID	String	Y	13	요청한 tr_id
tr_cont	연속 거래 여부	String	Y	1	F or M : 다음 데이터 있음
D or E : 마지막 데이터
gt_uid	Global UID	String	Y	32	거래고유번호
 */
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

  /*
rt_cd	성공 실패 여부	String	Y	1	0 : 성공
0 이외의 값 : 실패
msg_cd	응답코드	String	Y	8	응답코드
msg	msg	String	Y	80	응답메세지
output	응답상세	Array	Y		
-KRX_FWDG_ORD_ORGNO	한국거래소전송주문조직번호	String	Y	5	주문시 한국투자증권 시스템에서 지정된 영업점코드
-ODNO	주문번호	String	Y	10	주문시 한국투자증권 시스템에서 채번된 주문번호
-ORD_TMD	주문시각	String	Y	6	주문시각(시분초HHMMSS)
 */
  public static class ResBody {
    private final String rt_cd;
    private final String msg_cd;
    private final String msg1;
    private final ResBodyOuput output;

    public ResBody(String rt_cd, String msg_cd, String msg, ResBodyOuput output) {
      this.rt_cd = rt_cd;
      this.msg_cd = msg_cd;
      this.msg1 = msg;
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

