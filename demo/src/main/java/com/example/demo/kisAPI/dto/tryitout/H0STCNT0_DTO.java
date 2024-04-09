package com.example.demo.kisAPI.dto.tryitout;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class H0STCNT0_DTO {
/*
approval_key	웹소켓 접속키	String	Y	286	실시간 (웹소켓) 접속키 발급 API(/oauth2/Approval)를 사용하여 발급받은 웹소켓 접속키
custtype	고객타입	String	Y	1	B : 법인
P : 개인
tr_type	거래타입	String	Y	1	1 : 등록
2 : 해제
content-type	컨텐츠타입	String	Y	1	utf-8
 */

  @Data
  public static class ReqHeader {
    @NonNull
    private final String approval_key;
    @NonNull
    private final String custtype;
    @NonNull
    private final String tr_type;
    @NonNull
    private final String content_type;

    @NonNull
    public static ReqHeader from(@NonNull Account account, boolean isRegister) {
      String approval_key = account.getApproval_key();
      String tr_type = isRegister ? "1" : "2";
      return new ReqHeader(approval_key, "P", tr_type, "utf-8");
    }
  }

/*
tr_id	거래ID	String	Y	1	[실전/모의투자]
H0STCNT0 : 실시간 주식 체결가
tr_key	구분값	String	Y	1	종목번호 (6자리)
ETN의 경우, Q로 시작 (EX. Q500001)
 */
  @Data
  public static class ReqBody {
    @NonNull
    private final String tr_id;
    @NonNull
    private final String tr_key;

    @NonNull
    public static ReqBody from(@NonNull String code) {
      return new ReqBody("H0STCNT0", code);
    }
  }


/*
MKSC_SHRN_ISCD	유가증권 단축 종목코드	String	Y	9	
STCK_CNTG_HOUR	주식 체결 시간	String	Y	6	
STCK_PRPR	주식 현재가	Number	Y	4	
PRDY_VRSS_SIGN	전일 대비 부호	String	Y	1	1 : 상한
2 : 상승
3 : 보합
4 : 하한
5 : 하락
PRDY_VRSS	전일 대비	Number	Y	4	
PRDY_CTRT	전일 대비율	Number	Y	8	
WGHN_AVRG_STCK_PRC	가중 평균 주식 가격	Number	Y	8	
STCK_OPRC	주식 시가	Number	Y	4	
STCK_HGPR	주식 최고가	Number	Y	4	
STCK_LWPR	주식 최저가	Number	Y	4	
ASKP1	매도호가1	Number	Y	4	
BIDP1	매수호가1	Number	Y	4	
CNTG_VOL	체결 거래량	Number	Y	8	
ACML_VOL	누적 거래량	Number	Y	8	
ACML_TR_PBMN	누적 거래 대금	Number	Y	8	
SELN_CNTG_CSNU	매도 체결 건수	Number	Y	4	
SHNU_CNTG_CSNU	매수 체결 건수	Number	Y	4	
NTBY_CNTG_CSNU	순매수 체결 건수	Number	Y	4	
CTTR	체결강도	Number	Y	8	
SELN_CNTG_SMTN	총 매도 수량	Number	Y	8	
SHNU_CNTG_SMTN	총 매수 수량	Number	Y	8	
CCLD_DVSN	체결구분	String	Y	1	1:매수(+)
3:장전
5:매도(-)
SHNU_RATE	매수비율	Number	Y	8	
PRDY_VOL_VRSS_ACML_VOL_RATE	전일 거래량 대비 등락율	Number	Y	8	
OPRC_HOUR	시가 시간	String	Y	6	
OPRC_VRSS_PRPR_SIGN	시가대비구분	String	Y	1	1 : 상한
2 : 상승
3 : 보합
4 : 하한
5 : 하락
OPRC_VRSS_PRPR	시가대비	Number	Y	4	
HGPR_HOUR	최고가 시간	String	Y	6	
HGPR_VRSS_PRPR_SIGN	고가대비구분	String	Y	1	1 : 상한
2 : 상승
3 : 보합
4 : 하한
5 : 하락
HGPR_VRSS_PRPR	고가대비	Number	Y	4	
LWPR_HOUR	최저가 시간	String	Y	6	
LWPR_VRSS_PRPR_SIGN	저가대비구분	String	Y	1	1 : 상한
2 : 상승
3 : 보합
4 : 하한
5 : 하락
LWPR_VRSS_PRPR	저가대비	Number	Y	4	
BSOP_DATE	영업 일자	String	Y	8	
NEW_MKOP_CLS_CODE	신 장운영 구분 코드	String	Y	2	(1) 첫 번째 비트
1 : 장개시전
2 : 장중
3 : 장종료후
4 : 시간외단일가
7 : 일반Buy-in
8 : 당일Buy-in

(2) 두 번째 비트
0 : 보통
1 : 종가
2 : 대량
3 : 바스켓
7 : 정리매매
8 : Buy-in
TRHT_YN	거래정지 여부	String	Y	1	Y : 정지
N : 정상거래
ASKP_RSQN1	매도호가 잔량1	Number	Y	8	
BIDP_RSQN1	매수호가 잔량1	Number	Y	8	
TOTAL_ASKP_RSQN	총 매도호가 잔량	Number	Y	8	
TOTAL_BIDP_RSQN	총 매수호가 잔량	Number	Y	8	
VOL_TNRT	거래량 회전율	Number	Y	8	
PRDY_SMNS_HOUR_ACML_VOL	전일 동시간 누적 거래량	Number	Y	8	
PRDY_SMNS_HOUR_ACML_VOL_RATE	전일 동시간 누적 거래량 비율	Number	Y	8	
HOUR_CLS_CODE	시간 구분 코드	String	Y	1	0 : 장중
A : 장후예상
B : 장전예상
C : 9시이후의 예상가, VI발동
D : 시간외 단일가 예상
MRKT_TRTM_CLS_CODE	임의종료구분코드	String	Y	1	
VI_STND_PRC	정적VI발동기준가	Number	Y	4	
 */
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ResBody {
    private String MKSC_SHRN_ISCD;
    private String STCK_CNTG_HOUR;
    private String STCK_PRPR;
    private String PRDY_VRSS_SIGN;
    private String PRDY_VRSS;
    private String PRDY_CTRT;
    private String WGHN_AVRG_STCK_PRC;
    private String STCK_OPRC;
    private String STCK_HGPR;
    private String STCK_LWPR;
    private String ASKP1;
    private String BIDP1;
    private String CNTG_VOL;
    private String ACML_VOL;
    private String ACML_TR_PBMN;
    private String SELN_CNTG_CSNU;
    private String SHNU_CNTG_CSNU;
    private String NTBY_CNTG_CSNU;
    private String CTTR;
    private String SELN_CNTG_SMTN;
    private String SHNU_CNTG_SMTN;
    private String CCLD_DVSN;
    private String SHNU_RATE;
    private String PRDY_VOL_VRSS_ACML_VOL_RATE;
    private String OPRC_HOUR;
    private String OPRC_VRSS_PRPR_SIGN;
    private String OPRC_VRSS_PRPR;
    private String HGPR_HOUR;
    private String HGPR_VRSS_PRPR_SIGN;
    private String HGPR_VRSS_PRPR;
    private String LWPR_HOUR;
    private String LWPR_VRSS_PRPR_SIGN;
    private String LWPR_VRSS_PRPR;
    private String BSOP_DATE;
    private String NEW_MKOP_CLS_CODE;
    private String TRHT_YN;
    private String ASKP_RSQN1;
    private String BIDP_RSQN1;
    private String TOTAL_ASKP_RSQN;
    private String TOTAL_BIDP_RSQN;
    private String VOL_TNRT;
    private String PRDY_SMNS_HOUR_ACML_VOL;
    private String PRDY_SMNS_HOUR_ACML_VOL_RATE;
    private String HOUR_CLS_CODE;
    private String MRKT_TRTM_CLS_CODE;
    private String VI_STND_PRC;
  }
}
