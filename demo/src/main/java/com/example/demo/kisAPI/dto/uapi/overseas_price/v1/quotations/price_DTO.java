package com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

public class price_DTO {
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
tr_id	거래ID	String	Y	13	[실전투자/모의투자]
HHDFS00000300
tr_cont	연속 거래 여부	String	N	1	공백 : 초기 조회
N : 다음 데이터 조회 (output header의 tr_cont가 M일 경우)
custtype	고객타입	String	N	1	B : 법인
P : 개인
seq_no	일련번호	String	N	2	[법인 필수] 001
mac_address	맥주소	String	N	12	법인고객 혹은 개인고객의 Mac address 값
phone_number	핸드폰번호	String	N	12	[법인 필수] 제휴사APP을 사용하는 경우 사용자(회원) 핸드폰번호
ex) 01011112222 (하이픈 등 구분값 제거)
ip_addr	접속 단말 공인 IP	String	N	12	[법인 필수] 사용자(회원)의 IP Address
hashkey	해쉬키	String	N	256	[POST API 대상] Client가 요청하는 Request Body를 hashkey api로 생성한 Hash값
* API문서 > hashkey 참조
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
    // private final String personalseckey;
    @NonNull
    private final String tr_id;
    // private final String tr_cont;
    // private final String custtype;
    // private final String seq_no;
    // private final String mac_address;
    // private final String phone_number;
    // private final String ip_addr;
    // private final String hashkey;
    // private final String gt_uid;

    public ReqHeader(@NonNull String content_type, @NonNull String authorization, @NonNull String appkey, @NonNull String appsecret, @NonNull String tr_id) {
      this.content_type = content_type;
      this.authorization = authorization;
      this.appkey = appkey;
      this.appsecret = appsecret;
      this.tr_id = tr_id;
    }

    @NonNull
    public static ReqHeader from(@NonNull Account account) {
      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      String AUTHORIZATION = account.getAccessToken();
      if (APP_KEY == null || APP_SECRET == null || AUTHORIZATION == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      return new ReqHeader("application/json; charset=utf-8", "Bearer " + AUTHORIZATION, APP_KEY, APP_SECRET, "HHDFS00000300");
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
  }

  /*
AUTH	사용자권한정보	String	Y	32	"" (Null 값 설정)
EXCD	거래소코드	String	Y	4	HKS : 홍콩
NYS : 뉴욕
NAS : 나스닥
AMS : 아멕스
TSE : 도쿄
SHS : 상해
SZS : 심천
SHI : 상해지수
SZI : 심천지수
HSX : 호치민
HNX : 하노이
BAY : 뉴욕(주간)
BAQ : 나스닥(주간)
BAA : 아멕스(주간)
SYMB	종목코드	String	Y	16
   */
  public static class ReqQueryParam {
    @NonNull
    private final String AUTH;
    @NonNull
    private final String EXCD;
    @NonNull
    private final String SYMB;

    public ReqQueryParam(@NonNull String AUTH, @NonNull String EXCD, @NonNull String SYMB) {
      this.AUTH = AUTH;
      this.EXCD = EXCD;
      this.SYMB = SYMB;
    }

    @NonNull
    public static ReqQueryParam from(@NonNull String EXCD, @NonNull String SYMB) {
      return new ReqQueryParam("", EXCD, SYMB);
    }

    public String getAUTH() {
      return AUTH;
    }

    public String getEXCD() {
      return EXCD;
    }

    public String getSYMB() {
      return SYMB;
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
msg1	응답메세지	String	Y	80	응답메세지
output	응답상세	Object	Y		
-rsym	실시간조회종목코드	String	Y	16	D+시장구분(3자리)+종목코드
예) DNASAAPL : D+NAS(나스닥)+AAPL(애플)
[시장구분]
NYS : 뉴욕, NAS : 나스닥, AMS : 아멕스 ,
TSE : 도쿄, HKS : 홍콩,
SHS : 상해, SZS : 심천
HSX : 호치민, HNX : 하노이
-zdiv	소수점자리수	String	Y	1	
-base	전일종가	String	Y	12	전일의 종가
-pvol	전일거래량	String	Y	14	전일의 거래량
-last	현재가	String	Y	12	당일 조회시점의 현재 가격
-sign	대비기호	String	Y	1	1 : 상한
2 : 상승
3 : 보합
4 : 하한
5 : 하락
-diff	대비	String	Y	12	전일 종가와 당일 현재가의 차이 (당일 현재가-전일 종가)
-rate	등락율	String	Y	12	전일 대비 / 당일 현재가 * 100
-tvol	거래량	String	Y	14	당일 조회시점까지 전체 거래량
-tamt	거래대금	String	Y	14	당일 조회시점까지 전체 거래금액
-ordy	매수가능여부	String	Y	20	매수주문 가능 종목 여부
 */
  public static class ResBody {
    private final String rt_cd;
    private final String msg_cd;
    private final String msg1;
    private final ResBodyOutput output;

    public ResBody(String rt_cd, String msg_cd, String msg1, ResBodyOutput output) {
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

    public ResBodyOutput getOutput() {
      return output;
    }

    @Override
    public String toString() {
      return "ResBody [rt_cd=" + rt_cd + ", msg_cd=" + msg_cd + ", msg1=" + msg1 + ", output=" + output + "]";
    }
  }

  public static class ResBodyOutput {
    private final String rsym;
    private final String zdiv;
    private final String base;
    private final String pvol;
    private final String last;
    private final String sign;
    private final String diff;
    private final String rate;
    private final String tvol;
    private final String tamt;
    private final String ordy;

    public ResBodyOutput(String rsym, String zdiv, String base, String pvol, String last, String sign, String diff, String rate, String tvol, String tamt, String ordy) {
      this.rsym = rsym;
      this.zdiv = zdiv;
      this.base = base;
      this.pvol = pvol;
      this.last = last;
      this.sign = sign;
      this.diff = diff;
      this.rate = rate;
      this.tvol = tvol;
      this.tamt = tamt;
      this.ordy = ordy;
    }

    public String getRsym() {
      return rsym;
    }

    public String getZdiv() {
      return zdiv;
    }

    public String getBase() {
      return base;
    }

    public String getPvol() {
      return pvol;
    }

    public String getLast() {
      return last;
    }

    public String getSign() {
      return sign;
    }

    public String getDiff() {
      return diff;
    }

    public String getRate() {
      return rate;
    }

    public String getTvol() {
      return tvol;
    }

    public String getTamt() {
      return tamt;
    }

    public String getOrdy() {
      return ordy;
    }

    @Override
    public String toString() {
      return "ResBodyOutput [rsym=" + rsym + ", zdiv=" + zdiv + ", base=" + base + ", pvol=" + pvol + ", last=" + last
          + ", sign=" + sign + ", diff=" + diff + ", rate=" + rate + ", tvol=" + tvol + ", tamt=" + tamt + ", ordy="
          + ordy + "]";
    }
  }
}
