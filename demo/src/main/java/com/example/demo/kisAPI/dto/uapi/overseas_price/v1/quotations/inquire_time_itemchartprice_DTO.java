package com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations;

import java.util.List;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

public class inquire_time_itemchartprice_DTO {

  @Data
  @AllArgsConstructor
  public static class ReqHeader {
    @NonNull
    private final String content_type;
    @NonNull
    private final String authorization;
    @NonNull
    private final String appkey;
    @NonNull
    private final String appsecret;
    @NonNull
    private final String tr_id;

    public static ReqHeader from(@NonNull Account account) {
      String content_type = "application/json; charset=utf-8";
      String tr_id = "HHDFS76950200";

      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      String AUTHORIZATION = "Bearer " + account.getAccessToken();
      if (APP_KEY == null || APP_SECRET == null || AUTHORIZATION == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      return new ReqHeader(content_type, AUTHORIZATION, APP_KEY, APP_SECRET, tr_id);
    }
  }

  /*
  AUTH	사용자권한정보	String	Y	32	"" 공백으로 입력
EXCD	거래소코드	String	Y	4	NYS : 뉴욕
NAS : 나스닥
AMS : 아멕스
HKS : 홍콩
SHS : 상해
SZS : 심천
HSX : 호치민
HNX : 하노이
TSE : 도쿄
BAY : 뉴욕(주간)
BAQ : 나스닥(주간)
BAA : 아멕스(주간)
SYMB	종목코드	String	Y	16	종목코드(ex. TSLA)
NMIN	분갭	String	Y	4	분단위(1: 1분봉, 2: 2분봉, ...)
PINC	전일포함여부	String	Y	1	0:당일 1:전일포함
NEXT	다음여부	String	Y	1	"" 공백으로 입력
NREC	요청갯수	String	Y	4	레코드요청갯수 (최대 120)
FILL	미체결채움구분	String	Y	1	"" 공백으로 입력
KEYB	NEXT KEY BUFF	String	Y	32	"" 공백으로 입력
 */

  @Data
  @AllArgsConstructor
  public static class ReqQueryParam {
    @NonNull
    private final String AUTH;
    @NonNull
    private final String EXCD;
    @NonNull
    private final String SYMB;
    @NonNull
    private final String NMIN;
    @NonNull
    private final String PINC;
    @NonNull
    private final String NEXT;
    @NonNull
    private final String NREC;
    @NonNull
    private final String FILL;
    @NonNull
    private final String KEYB;

    @NonNull
    public static ReqQueryParam from(@NonNull Account account, @NonNull String code, @NonNull String minute) {
      String AUTH = "";
      String EXCD = "NAS";
      String SYMB = code;
      String NMIN = minute;
      String PINC = "1";
      String NEXT = "";
      String NREC = "120";
      String FILL = "";
      String KEYB = "";

      return new ReqQueryParam(AUTH, EXCD, SYMB, NMIN, PINC, NEXT, NREC, FILL, KEYB);
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

/*
rt_cd	성공 실패 여부	String	Y	1	
msg_cd	응답코드	String	Y	8	
msg1	응답메세지	String	Y	80	
Output1	응답상세	Object Array	Y		
-rsym	실시간종목코드	String	Y	16	
-zdiv	소수점자리수	String	Y	1	
-stim	장시작현지시간	String	Y	6	
-etim	장종료현지시간	String	Y	6	
-sktm	장시작한국시간	String	Y	6	
-ektm	장종료한국시간	String	Y	6	
-next	다음가능여부	String	Y	1	
-more	추가데이타여부	String	Y	1	
-nrec	레코드갯수	String	Y	4	
Output2	응답상세2	Object	Y		array
-tymd	현지영업일자	String	Y	8	
-xymd	현지기준일자	String	Y	8	
-xhms	현지기준시간	String	Y	6	
-kymd	한국기준일자	String	Y	8	
-khms	한국기준시간	String	Y	6	
-open	시가	String	Y	12	
-high	고가	String	Y	12	
-low	저가	String	Y	12	
-last	종가	String	Y	12	
-evol	체결량	String	Y	12	
-eamt	체결대금	String	Y	14
*/

  @Data
  @AllArgsConstructor
  @ToString
  public static class ResBody {
    private final String rt_cd;
    private final String msg_cd;
    private final String msg1;
    private final Output1 Output1;
    private final List<Output2> Output2;
  }

  @Data
  @AllArgsConstructor
  @ToString
  public static class Output1 {
    private final String rsym;
    private final String zdiv;
    private final String stim;
    private final String etim;
    private final String sktm;
    private final String ektm;
    private final String next;
    private final String more;
    private final String nrec;
  }

  @Data
  @AllArgsConstructor
  @ToString
  public static class Output2 {
    private final String tymd;
    private final String xymd;
    private final String xhms;
    private final String kymd;
    private final String khms;
    private final String open;
    private final String high;
    private final String low;
    private final String last;
    private final String evol;
    private final String eamt;
  }

}
