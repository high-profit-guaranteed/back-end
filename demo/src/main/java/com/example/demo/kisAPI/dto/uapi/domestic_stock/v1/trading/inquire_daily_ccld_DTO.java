package com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading;

import java.util.List;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

import lombok.Data;

public class inquire_daily_ccld_DTO {
  /*
   * content-type 컨텐츠타입 String N 40 application/json; charset=utf-8
   * authorization 접근토큰 String Y 40 OAuth 토큰이 필요한 API 경우 발급한 Access token
   * 일반고객(Access token 유효기간 1일, OAuth 2.0의 Client Credentials Grant 절차를 준용)
   * 법인(Access token 유효기간 3개월, Refresh token 유효기간 1년, OAuth 2.0의 Authorization
   * Code Grant 절차를 준용)
   * appkey 앱키 String Y 36 한국투자증권 홈페이지에서 발급받은 appkey (절대 노출되지 않도록 주의해주세요.)
   * appsecret 앱시크릿키 String Y 180 한국투자증권 홈페이지에서 발급받은 appsecret (절대 노출되지 않도록
   * 주의해주세요.)
   * personalseckey 고객식별키 String N 180 [법인 필수] 제휴사 회원 관리를 위한 고객식별키
   * tr_id 거래ID String Y 13 [실전투자]
   * TTTC8001R : 주식 일별 주문 체결 조회(3개월이내)
   * CTSC9115R : 주식 일별 주문 체결 조회(3개월이전)
   * 
   * [모의투자]
   * VTTC8001R : 주식 일별 주문 체결 조회(3개월이내)
   * VTSC9115R : 주식 일별 주문 체결 조회(3개월이전)
   * 일별 조회로, 당일 주문내역은 지연될 수 있습니다.
   * 3개월이내 기준: 개월수로 3개월
   * 오늘이 4월22일이면 TTC8001R에서 1월~ 3월 + 4월 조회 가능
   * 5월이 되면 1월 데이터는 TTC8001R에서 조회 불가, TSC9115R로 조회 가능
   * tr_cont 연속 거래 여부 String N 1 공백 : 초기 조회
   * N : 다음 데이터 조회 (output header의 tr_cont가 M일 경우)
   * custtype 고객타입 String N 1 B : 법인
   * P : 개인
   * seq_no 일련번호 String N 2 [법인 필수] 001
   * mac_address 맥주소 String N 12 법인고객 혹은 개인고객의 Mac address 값
   * phone_number 핸드폰번호 String N 12 [법인 필수] 제휴사APP을 사용하는 경우 사용자(회원) 핸드폰번호
   * ex) 01011112222 (하이픈 등 구분값 제거)
   * ip_addr 접속 단말 공인 IP String N 12 [법인 필수] 사용자(회원)의 IP Address
   * hashkey 해쉬키 String N 256 [POST API 대상] Client가 요청하는 Request Body를 hashkey
   * api로 생성한 Hash값
   * API문서 > hashkey 참조
   * gt_uid Global UID String N 32 [법인 필수] 거래고유번호로 사용하므로 거래별로 UNIQUE해야 함
   */
  @Data
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

    @NonNull
    public static ReqHeader from(@NonNull Account account, boolean isIn3Months) {
      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      String AUTHORIZATION = account.getAccessToken();
      if (APP_KEY == null || APP_SECRET == null || AUTHORIZATION == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      String tr_id;
      if (account.isVirtual()) {
        tr_id = isIn3Months ? "VTTC8001R" : "VTSC9115R";
      } else {
        tr_id = isIn3Months ? "TTTC8001R" : "CTSC9115R";
      }

      return new ReqHeader("application/json; charset=utf-8", "Bearer " + AUTHORIZATION, APP_KEY, APP_SECRET, tr_id);
    }
  }

  /*
   * CANO 종합계좌번호 String Y 8 계좌번호 체계(8-2)의 앞 8자리
   * ACNT_PRDT_CD 계좌상품코드 String Y 2 계좌번호 체계(8-2)의 뒤 2자리
   * INQR_STRT_DT 조회시작일자 String Y 8 YYYYMMDD
   * INQR_END_DT 조회종료일자 String Y 8 YYYYMMDD
   * SLL_BUY_DVSN_CD 매도매수구분코드 String Y 2 00 : 전체
   * 01 : 매도
   * 02 : 매수
   * INQR_DVSN 조회구분 String Y 2 00 : 역순
   * 01 : 정순
   * PDNO 상품번호 String Y 12 종목번호(6자리)
   * 공란 : 전체 조회
   * CCLD_DVSN 체결구분 String Y 2 00 : 전체
   * 01 : 체결
   * 02 : 미체결
   * ORD_GNO_BRNO 주문채번지점번호 String Y 5 "" (Null 값 설정)
   * ODNO 주문번호 String Y 10 "" (Null 값 설정)
   * INQR_DVSN_3 조회구분3 String Y 2 00 : 전체
   * 01 : 현금
   * 02 : 융자
   * 03 : 대출
   * 04 : 대주
   * INQR_DVSN_1 조회구분1 String Y 1 공란 : 전체
   * 1 : ELW
   * 2 : 프리보드
   * CTX_AREA_FK100 연속조회검색조건100 String Y 100 공란 : 최초 조회시
   * 이전 조회 Output CTX_AREA_FK100 값 : 다음페이지 조회시(2번째부터)
   * CTX_AREA_NK100 연속조회키100 String Y 100 공란 : 최초 조회시
   * 이전 조회 Output CTX_AREA_NK100 값 : 다음페이지 조회시(2번째부터)
   */
  @Data
  public static class ReqQueryParam {
    @NonNull
    private final String CANO;
    @NonNull
    private final String ACNT_PRDT_CD;
    @NonNull
    private final String INQR_STRT_DT;
    @NonNull
    private final String INQR_END_DT;
    @NonNull
    private final String SLL_BUY_DVSN_CD;
    @NonNull
    private final String INQR_DVSN;
    @NonNull
    private final String PDNO;
    @NonNull
    private final String CCLD_DVSN;
    @NonNull
    private final String ORD_GNO_BRNO;
    @NonNull
    private final String ODNO;
    @NonNull
    private final String INQR_DVSN_3;
    @NonNull
    private final String INQR_DVSN_1;
    @NonNull
    private final String CTX_AREA_FK100;
    @NonNull
    private final String CTX_AREA_NK100;

    @NonNull
    public static ReqQueryParam from(@NonNull Account account, @NonNull String INQR_STRT_DT,
        @NonNull String INQR_END_DT, String PDNO) {
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

      return new ReqQueryParam(CANO, ACNT_PRDT_CD, INQR_STRT_DT, INQR_END_DT, "00", "00", PDNO==null?"":PDNO, "00", "", "", "00", "",
          "", "");
    }

    @NonNull
    public static ReqQueryParam from(@NonNull Account account, @NonNull String INQR_STRT_DT,
        @NonNull String INQR_END_DT, boolean isBuy, String PDNO) {
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

      String SLL_BUY_DVSN_CD = isBuy ? "02" : "01";

      return new ReqQueryParam(CANO, ACNT_PRDT_CD, INQR_STRT_DT, INQR_END_DT, SLL_BUY_DVSN_CD, "00", PDNO==null?"":PDNO, "00", "", "",
          "00", "", "", "");
    }
  }

  /*
   * content-type 컨텐츠타입 String Y 40 application/json; charset=utf-8
   * tr_id 거래ID String Y 13 요청한 tr_id
   * tr_cont 연속 거래 여부 String Y 1 F or M : 다음 데이터 있음
   * D or E : 마지막 데이터
   * gt_uid Global UID String Y 32 거래고유번호
   */
  @Data
  public static class ResHeader {
    private final String content_type;
    private final String tr_id;
    private final String tr_cont;
    private final String gt_uid;
  }

  /*
   * rt_cd 성공 실패 여부 String Y 1 0 : 성공
   * 0 이외의 값 : 실패
   * msg_cd 응답코드 String Y 8 응답코드
   * msg1 응답메세지 String Y 80 응답메세지
   * ctx_area_fk100 연속조회검색조건100 String Y 100
   * ctx_area_nk100 연속조회키100 String Y 100
   * output1 응답상세1 Array Y
   * -ord_dt 주문일자 String Y 8 주문일자
   * -ord_gno_brno 주문채번지점번호 String Y 5 주문시 한국투자증권 시스템에서 지정된 영업점코드
   * -odno 주문번호 String Y 10 주문시 한국투자증권 시스템에서 채번된 주문번호, 지점별 일자별로 채번됨
   * 주문번호 유일조건: ord_dt(주문일자) + ord_gno_brno(주문채번지점번호) + odno(주문번호)
   * -orgn_odno 원주문번호 String Y 10 이전 주문에 채번된 주문번호
   * -ord_dvsn_name 주문구분명 String Y 60
   * -sll_buy_dvsn_cd 매도매수구분코드 String Y 2 01 : 매도
   * 02 : 매수
   * -sll_buy_dvsn_cd_name 매도매수구분코드명 String Y 60 반대매매 인경우 "임의매도"로 표시됨
   * 
   * 정정취소여부가 Y이면 *이 붙음
   * ex) 매수취소* = 매수취소가 완료됨
   * -pdno 상품번호 String Y 12 종목번호(6자리)
   * -prdt_name 상품명 String Y 60 종목명
   * -ord_qty 주문수량 String Y 10
   * -ord_unpr 주문단가 String Y 19
   * -ord_tmd 주문시각 String Y 6
   * -tot_ccld_qty 총체결수량 String Y 10
   * -avg_prvs 평균가 String Y 19 체결평균가 ( 총체결금액 / 총체결수량 )
   * -cncl_yn 취소여부 String Y 1
   * -tot_ccld_amt 총체결금액 String Y 19
   * -loan_dt 대출일자 String Y 8
   * -ord_dvsn_cd 주문구분코드 String Y 2 00 : 지정가
   * 01 : 시장가
   * 02 : 조건부지정가
   * 03 : 최유리지정가
   * 04 : 최우선지정가
   * 05 : 장전 시간외
   * 06 : 장후 시간외
   * 07 : 시간외 단일가
   * 08 : 자기주식
   * 09 : 자기주식S-Option
   * 10 : 자기주식금전신탁
   * 11 : IOC지정가 (즉시체결,잔량취소)
   * 12 : FOK지정가 (즉시체결,전량취소)
   * 13 : IOC시장가 (즉시체결,잔량취소)
   * 14 : FOK시장가 (즉시체결,전량취소)
   * 15 : IOC최유리 (즉시체결,잔량취소)
   * 16 : FOK최유리 (즉시체결,전량취소)
   * -cncl_cfrm_qty 취소확인수량 String Y 10
   * -rmn_qty 잔여수량 String Y 10
   * -rjct_qty 거부수량 String Y 10
   * -ccld_cndt_name 체결조건명 String Y 10
   * -infm_tmd 통보시각 String Y 6 ※ 실전투자계좌로는 해당값이 제공되지 않습니다.
   * -ctac_tlno 연락전화번호 String Y 20
   * -prdt_type_cd 상품유형코드 String Y 3 300 : 주식
   * 301 : 선물옵션
   * 302 : 채권
   * 306 : ELS
   * -excg_dvsn_cd 거래소구분코드 String Y 2 01 : 한국증권
   * 02 : 증권거래소
   * 03 : 코스닥
   * 04 : K-OTC
   * 05 : 선물거래소
   * 06 : CME
   * 07 : EUREX
   * 21 : 금현물
   * 51 : 홍콩
   * 52 : 상해B
   * 53 : 심천
   * 54 : 홍콩거래소
   * 55 : 미국
   * 56 : 일본
   * 57 : 상해A
   * 58 : 심천A
   * 59 : 베트남
   * 61 : 장전시간외시장
   * 64 : 경쟁대량매매
   * 65 : 경매매시장
   * 81 : 시간외단일가시장
   * output2 응답상세2 Array Y
   * -tot_ord_qty 총주문수량 String Y 10 미체결주문수량 + 체결수량 (취소주문제외)
   * -tot_ccld_qty 총체결수량 String Y 10
   * -pchs_avg_pric 매입평균가격 String Y 22 총체결금액 / 총체결수량
   * -tot_ccld_amt 총체결금액 String Y 19
   * -prsm_tlex_smtl 추정제비용합계 String Y 19 제세 + 주문수수료
   * ※ 해당 값은 당일 데이터에 대해서만 제공됩니다.
   */
  @Data
  public static class ResBody {
    private final String rt_cd;
    private final String msg_cd;
    private final String msg1;
    private final String ctx_area_fk100;
    private final String ctx_area_nk100;
    private final List<ResBodyOutput1> output1;
    private final ResBodyOutput2 output2;
  }

  @Data
  public static class ResBodyOutput1 {
    private final String ord_dt;
    private final String ord_gno_brno;
    private final String odno;
    private final String orgn_odno;
    private final String ord_dvsn_name;
    private final String sll_buy_dvsn_cd;
    private final String sll_buy_dvsn_cd_name;
    private final String pdno;
    private final String prdt_name;
    private final String ord_qty;
    private final String ord_unpr;
    private final String ord_tmd;
    private final String tot_ccld_qty;
    private final String avg_prvs;
    private final String cncl_yn;
    private final String tot_ccld_amt;
    private final String loan_dt;
    private final String ord_dvsn_cd;
    private final String cncl_cfrm_qty;
    private final String rmn_qty;
    private final String rjct_qty;
    private final String ccld_cndt_name;
    private final String infm_tmd;
    private final String ctac_tlno;
    private final String prdt_type_cd;
    private final String excg_dvsn_cd;
  }

  @Data
  public static class ResBodyOutput2 {
    private final String tot_ord_qty;
    private final String tot_ccld_qty;
    private final String pchs_avg_pric;
    private final String tot_ccld_amt;
    private final String prsm_tlex_smtl;
  }
}
