package com.example.demo.kisAPI.dto.tryitout;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class HDFSCNT0_DTO {

  @Data
  public static class ReqHeader {
    @NonNull
    private final String approval_key;
    @NonNull
    private final String tr_type;
    @NonNull
    private final String custtype;
    @NonNull
    private final String content_type;

    @NonNull
    public static ReqHeader from(@NonNull Account account, boolean isRegister) {
      String approval_key = account.getApproval_key();
      String tr_type = isRegister ? "1" : "2";
      return new ReqHeader(approval_key, tr_type, "P", "utf-8");
    }
  }

  @Data
  public static class ReqBody {
    @NonNull
    private final String tr_id;
    @NonNull
    private final String tr_key;

    @NonNull
    public static ReqBody from(@NonNull String code, boolean isDay) {
      String tr_key = isDay ? "DNAS"+code : "RBAQ"+code;
      return new ReqBody("HDFSCNT0", tr_key);
    }
  }


/*
RSYM	실시간종목코드	String	Y	16	'각 항목사이에는 구분자로 ^ 사용,
모든 데이터타입은 String으로 변환되어 push 처리됨'
SYMB	종목코드	String	Y	16	
ZDIV	수수점자리수	String	Y	1	
TYMD	현지영업일자	String	Y	8	
XYMD	현지일자	String	Y	6	
XHMS	현지시간	String	Y	6	
KYMD	한국일자	String	Y	6	
KHMS	한국시간	String	Y	6	
OPEN	시가	String	Y	6	
HIGH	고가	String	Y	6	
LOW	저가	String	Y	6	
LAST	현재가	String	Y	6	
SIGN	대비구분	String	Y	6	
DIFF	전일대비	String	Y	8	
RATE	등락율	String	Y	6	
PBID	매수호가	String	Y	10	
PASK	매도호가	String	Y	10	
VBID	매수잔량	String	Y	10	
VASK	매도잔량	String	Y	10	
EVOL	체결량	String	Y	12	
TVOL	거래량	String	Y	12	
TAMT	거래대금	String	Y	10	
BIVL	매도체결량	String	Y	10	매수호가가 매도주문 수량을 따라가서 체결된것을 표현하여 BIVL 이라는 표현을 사용
ASVL	매수체결량	String	Y	10	매도호가가 매수주문 수량을 따라가서 체결된것을 표현하여 ASVL 이라는 표현을 사용
STRN	체결강도	String	Y	10	
MTYP	시장구분 1:장중,2:장전,3:장후	String	Y	10	
 */
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ResBody {
    private String RSYM;
    private String SYMB;
    private String ZDIV;
    private String TYMD;
    private String XYMD;
    private String XHMS;
    private String KYMD;
    private String KHMS;
    private String OPEN;
    private String HIGH;
    private String LOW;
    private String LAST;
    private String SIGN;
    private String DIFF;
    private String RATE;
    private String PBID;
    private String PASK;
    private String VBID;
    private String VASK;
    private String EVOL;
    private String TVOL;
    private String TAMT;
    private String BIVL;
    private String ASVL;
    private String STRN;
    private String MTYP;
  }
}
