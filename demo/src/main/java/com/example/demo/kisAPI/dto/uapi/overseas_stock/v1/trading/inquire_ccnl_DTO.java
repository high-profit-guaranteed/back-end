package com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading;

import java.util.List;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

public class inquire_ccnl_DTO {
  /*
   * content-type 컨텐츠타입 String N 40 application/json; charset=utf-8
   * authorization 접근토큰 String Y 40 OAuth 토큰이 필요한 API 경우 발급한 Access token
   * 일반고객(Access token 유효기간 1일, OAuth 2.0의 Client Credentials Grant 절차를 준용)
   * 법인(Access token 유효기간 3개월, Refresh token 유효기간 1년, OAuth 2.0의 Authorization
   * Code Grant 절차를 준용)
   * 
   * ※ 토큰 지정시 토큰 타입("Bearer") 지정 필요. 즉, 발급받은 접근토큰 앞에 앞에 "Bearer" 붙여서 호출
   * EX) "Bearer eyJ..........8GA"
   * appkey 앱키 String Y 36 한국투자증권 홈페이지에서 발급받은 appkey (절대 노출되지 않도록 주의해주세요.)
   * appsecret 앱시크릿키 String Y 180 한국투자증권 홈페이지에서 발급받은 appsecret (절대 노출되지 않도록
   * 주의해주세요.)
   * personalseckey 고객식별키 String N 180 [법인 필수] 제휴사 회원 관리를 위한 고객식별키
   * tr_id 거래ID String Y 13 [실전투자]
   * TTTS3035R
   * 
   * [모의투자]
   * VTTS3035R
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
  public static class ReqHeader {
    @NonNull
    private final String content_type;
    @NonNull
    private final String authorization;
    @NonNull
    private final String appkey;
    @NonNull
    private final String appsecret;
    // private String personalseckey;
    @NonNull
    private final String tr_id;
    // private String tr_cont;
    // private String custtype;
    // private String seq_no;
    // private String mac_address;
    // private String phone_number;
    // private String ip_addr;
    // private String hashkey;
    // private String gt_uid;

    public ReqHeader(@NonNull String content_type, @NonNull String authorization, @NonNull String appkey,
        @NonNull String appsecret, @NonNull String tr_id) {
      this.content_type = content_type;
      this.authorization = authorization;
      this.appkey = appkey;
      this.appsecret = appsecret;
      this.tr_id = tr_id;
    }

    @NonNull
    public static ReqHeader from(@NonNull Account account) {
      String tr_id;
      if (account.isVirtual()) {
        tr_id = "VTTS3035R";
      } else {
        tr_id = "TTTS3035R";
      }

      String APP_KEY = account.getAPP_KEY();
      String APP_SECRET = account.getAPP_SECRET();
      String AUTHORIZATION = account.getAccessToken();
      if (APP_KEY == null || APP_SECRET == null || AUTHORIZATION == null) {
        throw new IllegalStateException("계좌 정보가 올바르지 않습니다.");
      }

      return new ReqHeader("application/json; charset=utf-8", "Bearer " + AUTHORIZATION, APP_KEY, APP_SECRET, tr_id);
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
   * CANO 종합계좌번호 String Y 8 계좌번호 체계(8-2)의 앞 8자리
   * ACNT_PRDT_CD 계좌상품코드 String Y 2 계좌번호 체계(8-2)의 뒤 2자리
   * PDNO 상품번호 String Y 12 전종목일 경우 "%" 입력
   * ※ 모의투자계좌의 경우 ""(전체 조회)만 가능
   * ORD_STRT_DT 주문시작일자 String Y 8 YYYYMMDD 형식 (현지시각 기준)
   * ORD_END_DT 주문종료일자 String Y 8 YYYYMMDD 형식 (현지시각 기준)
   * SLL_BUY_DVSN 매도매수구분 String Y 2 00 : 전체
   * 01 : 매도
   * 02 : 매수
   * ※ 모의투자계좌의 경우 "00"(전체 조회)만 가능
   * CCLD_NCCS_DVSN 체결미체결구분 String Y 2 00 : 전체
   * 01 : 체결
   * 02 : 미체결
   * ※ 모의투자계좌의 경우 "00"(전체 조회)만 가능
   * OVRS_EXCG_CD 해외거래소코드 String Y 4 전종목일 경우 "%" 입력
   * NASD : 미국시장 전체(나스닥, 뉴욕, 아멕스)
   * NYSE : 뉴욕
   * AMEX : 아멕스
   * SEHK : 홍콩
   * SHAA : 중국상해
   * SZAA : 중국심천
   * TKSE : 일본
   * HASE : 베트남 하노이
   * VNSE : 베트남 호치민
   * ※ 모의투자계좌의 경우 ""(전체 조회)만 가능
   * SORT_SQN 정렬순서 String Y 2 DS : 정순
   * AS : 역순
   * ORD_DT 주문일자 String Y 8 "" (Null 값 설정)
   * ORD_GNO_BRNO 주문채번지점번호 String Y 5 "" (Null 값 설정)
   * ODNO 주문번호 String Y 10 "" (Null 값 설정)
   * ※ 주문번호로 검색 불가능합니다. 반드시 ""(Null 값 설정) 바랍니다.
   * CTX_AREA_NK200 연속조회키200 String Y 200 공란 : 최초 조회시
   * 이전 조회 Output CTX_AREA_NK200값 : 다음페이지 조회시(2번째부터)
   * CTX_AREA_FK200 연속조회검색조건200 String Y 200 공란 : 최초 조회시
   * 이전 조회 Output CTX_AREA_FK200값 : 다음페이지 조회시(2번째부터)
   */
  public static class ReqQueryParam {
    @NonNull
    private final String CANO;
    @NonNull
    private final String ACNT_PRDT_CD;
    @NonNull
    private final String PDNO;
    @NonNull
    private final String ORD_STRT_DT;
    @NonNull
    private final String ORD_END_DT;
    @NonNull
    private final String SLL_BUY_DVSN;
    @NonNull
    private final String CCLD_NCCS_DVSN;
    @NonNull
    private final String OVRS_EXCG_CD;
    @NonNull
    private final String SORT_SQN;
    @NonNull
    private final String ORD_DT;
    @NonNull
    private final String ORD_GNO_BRNO;
    @NonNull
    private final String ODNO;
    @NonNull
    private final String CTX_AREA_NK200;
    @NonNull
    private final String CTX_AREA_FK200;

    public ReqQueryParam(@NonNull String CANO, @NonNull String ACNT_PRDT_CD, @NonNull String PDNO,
        @NonNull String ORD_STRT_DT,
        @NonNull String ORD_END_DT, @NonNull String SLL_BUY_DVSN, @NonNull String CCLD_NCCS_DVSN,
        @NonNull String OVRS_EXCG_CD,
        @NonNull String SORT_SQN, @NonNull String ORD_DT, @NonNull String ORD_GNO_BRNO, @NonNull String ODNO,
        @NonNull String CTX_AREA_NK200, @NonNull String CTX_AREA_FK200) {
      this.CANO = CANO;
      this.ACNT_PRDT_CD = ACNT_PRDT_CD;
      this.PDNO = PDNO;
      this.ORD_STRT_DT = ORD_STRT_DT;
      this.ORD_END_DT = ORD_END_DT;
      this.SLL_BUY_DVSN = SLL_BUY_DVSN;
      this.CCLD_NCCS_DVSN = CCLD_NCCS_DVSN;
      this.OVRS_EXCG_CD = OVRS_EXCG_CD;
      this.SORT_SQN = SORT_SQN;
      this.ORD_DT = ORD_DT;
      this.ORD_GNO_BRNO = ORD_GNO_BRNO;
      this.ODNO = ODNO;
      this.CTX_AREA_NK200 = CTX_AREA_NK200;
      this.CTX_AREA_FK200 = CTX_AREA_FK200;
    }

    @NonNull
    public static ReqQueryParam from(@NonNull Account account, @NonNull String start, @NonNull String end) {
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

      return new ReqQueryParam(CANO, ACNT_PRDT_CD, account.isVirtual()?"":"%", start, end, "00", "00", account.isVirtual()?"":"NASD", "DS", "", "", "", "", "");
    }

    @NonNull
    public static ReqQueryParam from(@NonNull Account account, @NonNull String start, @NonNull String end, @NonNull String ctx_area_fk200, @NonNull String ctx_area_nk200) {
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

      return new ReqQueryParam(CANO, ACNT_PRDT_CD, account.isVirtual()?"":"%", start, end, "00", "00", account.isVirtual()?"":"NASD", "DS", "", "", "", ctx_area_nk200, ctx_area_fk200);
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

    public String getORD_STRT_DT() {
      return ORD_STRT_DT;
    }

    public String getORD_END_DT() {
      return ORD_END_DT;
    }

    public String getSLL_BUY_DVSN() {
      return SLL_BUY_DVSN;
    }

    public String getCCLD_NCCS_DVSN() {
      return CCLD_NCCS_DVSN;
    }

    public String getOVRS_EXCG_CD() {
      return OVRS_EXCG_CD;
    }

    public String getSORT_SQN() {
      return SORT_SQN;
    }

    public String getORD_DT() {
      return ORD_DT;
    }

    public String getORD_GNO_BRNO() {
      return ORD_GNO_BRNO;
    }

    public String getODNO() {
      return ODNO;
    }

    public String getCTX_AREA_NK200() {
      return CTX_AREA_NK200;
    }

    public String getCTX_AREA_FK200() {
      return CTX_AREA_FK200;
    }
  }

  /*
   * content-type 컨텐츠타입 String Y 40 application/json; charset=utf-8
   * tr_id 거래ID String Y 13 요청한 tr_id
   * tr_cont 연속 거래 여부 String Y 1 F or M : 다음 데이터 있음
   * D or E : 마지막 데이터
   * gt_uid Global UID String Y 32 거래고유번호
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

    @Override
    public String toString() {
      return "ResHeader [content_type=" + content_type + ", tr_id=" + tr_id + ", tr_cont=" + tr_cont + ", gt_uid="
          + gt_uid + "]";
    }
  }

  /*
   * rt_cd 성공 실패 여부 String Y 1 0 : 성공
   * 0 이외의 값 : 실패
   * msg_cd 응답코드 String Y 8 응답코드
   * msg1 응답메세지 String Y 80 응답메세지
   * ctx_area_fk200 연속조회검색조건200 String Y 200
   * ctx_area_nk200 연속조회키200 String Y 200
   * output 응답상세 Array Y
   * -ord_dt 주문일자 String Y 8 주문접수 일자 (현지시각 기준)
   * -ord_gno_brno 주문채번지점번호 String Y 5 계좌 개설 시 관리점으로 선택한 영업점의 고유번호
   * -odno 주문번호 String Y 10 접수한 주문의 일련번호
   * ※ 정정취소주문 시, 해당 값 odno(주문번호) 넣어서 사용
   * -orgn_odno 원주문번호 String Y 10 정정 또는 취소 대상 주문의 일련번호
   * -sll_buy_dvsn_cd 매도매수구분코드 String Y 2 01 : 매도
   * 02 : 매수
   * -sll_buy_dvsn_cd_name 매도매수구분코드명 String Y 60
   * -rvse_cncl_dvsn 정정취소구분 String Y 2 01 : 정정
   * 02 : 취소
   * -rvse_cncl_dvsn_name 정정취소구분명 String Y 60
   * -pdno 상품번호 String Y 12
   * -prdt_name 상품명 String Y 60
   * -ft_ord_qty FT주문수량 String Y 10 주문수량
   * -ft_ord_unpr3 FT주문단가3 String Y 26 주문가격
   * -ft_ccld_qty FT체결수량 String Y 10 체결된 수량
   * -ft_ccld_unpr3 FT체결단가3 String Y 26 체결된 가격
   * -ft_ccld_amt3 FT체결금액3 String Y 23 체결된 금액
   * -nccs_qty 미체결수량 String Y 10 미체결수량
   * -prcs_stat_name 처리상태명 String Y 60
   * -rjct_rson 거부사유 String Y 60 정상 처리되지 못하고 거부된 주문의 사유
   * -ord_tmd 주문시각 String Y 6 주문 접수 시간
   * -tr_mket_name 거래시장명 String Y 60
   * -tr_natn 거래국가 String Y 3
   * -tr_natn_name 거래국가명 String Y 3
   * -ovrs_excg_cd 해외거래소코드 String Y 4 NASD : 나스닥
   * NYSE : 뉴욕
   * AMEX : 아멕스
   * SEHK : 홍콩
   * SHAA : 중국상해
   * SZAA : 중국심천
   * TKSE : 일본
   * HASE : 베트남 하노이
   * VNSE : 베트남 호치민
   * -tr_crcy_cd 거래통화코드 String Y 60
   * -dmst_ord_dt 국내주문일자 String Y 8
   * -thco_ord_tmd 당사주문시각 String Y 6
   * -loan_type_cd 대출유형코드 String Y 2
   * -mdia_dvsn_name 매체구분명 String Y 60 ex) OpenAPI, 모바일
   * -loan_dt 대출일자 String Y 8
   * -rjct_rson_name 거부사유명 String Y 60
   * -usa_amk_exts_rqst_yn 미국애프터마켓연장신청여부 String Y 1 Y/N
   */
  public static class ResBody {
    private final String rt_cd;
    private final String msg_cd;
    private final String msg1;
    private final String ctx_area_fk200;
    private final String ctx_area_nk200;
    private final List<ResBodyOutput> output;

    public ResBody(String rt_cd, String msg_cd, String msg1, String ctx_area_fk200, String ctx_area_nk200,
        List<ResBodyOutput> output) {
      this.rt_cd = rt_cd;
      this.msg_cd = msg_cd;
      this.msg1 = msg1;
      this.ctx_area_fk200 = ctx_area_fk200;
      this.ctx_area_nk200 = ctx_area_nk200;
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

    public String getCtx_area_fk200() {
      return ctx_area_fk200;
    }

    public String getCtx_area_nk200() {
      return ctx_area_nk200;
    }

    public List<ResBodyOutput> getOutput() {
      return output;
    }

    @Override
    public String toString() {
      return "ResBody [rt_cd=" + rt_cd + ", msg_cd=" + msg_cd + ", msg1=" + msg1 + ", ctx_area_fk200=" + ctx_area_fk200
          + ", ctx_area_nk200=" + ctx_area_nk200 + ", output=" + output + "]";
    }

    
  }

  public static class ResBodyOutput {
    private final String ord_dt;
    private final String ord_gno_brno;
    private final String odno;
    private final String orgn_odno;
    private final String sll_buy_dvsn_cd;
    private final String sll_buy_dvsn_cd_name;
    private final String rvse_cncl_dvsn;
    private final String rvse_cncl_dvsn_name;
    private final String pdno;
    private final String prdt_name;
    private final String ft_ord_qty;
    private final String ft_ord_unpr3;
    private final String ft_ccld_qty;
    private final String ft_ccld_unpr3;
    private final String ft_ccld_amt3;
    private final String nccs_qty;
    private final String prcs_stat_name;
    private final String rjct_rson;
    private final String ord_tmd;
    private final String tr_mket_name;
    private final String tr_natn;
    private final String tr_natn_name;
    private final String ovrs_excg_cd;
    private final String tr_crcy_cd;
    private final String dmst_ord_dt;
    private final String thco_ord_tmd;
    private final String loan_type_cd;
    private final String mdia_dvsn_name;
    private final String loan_dt;
    private final String rjct_rson_name;
    private final String usa_amk_exts_rqst_yn;

    public ResBodyOutput(String ord_dt, String ord_gno_brno, String odno, String orgn_odno, String sll_buy_dvsn_cd,
        String sll_buy_dvsn_cd_name, String rvse_cncl_dvsn, String rvse_cncl_dvsn_name, String pdno, String prdt_name,
        String ft_ord_qty, String ft_ord_unpr3, String ft_ccld_qty, String ft_ccld_unpr3, String ft_ccld_amt3,
        String nccs_qty, String prcs_stat_name, String rjct_rson, String ord_tmd, String tr_mket_name, String tr_natn,
        String tr_natn_name, String ovrs_excg_cd, String tr_crcy_cd, String dmst_ord_dt, String thco_ord_tmd,
        String loan_type_cd, String mdia_dvsn_name, String loan_dt, String rjct_rson_name,
        String usa_amk_exts_rqst_yn) {
      this.ord_dt = ord_dt;
      this.ord_gno_brno = ord_gno_brno;
      this.odno = odno;
      this.orgn_odno = orgn_odno;
      this.sll_buy_dvsn_cd = sll_buy_dvsn_cd;
      this.sll_buy_dvsn_cd_name = sll_buy_dvsn_cd_name;
      this.rvse_cncl_dvsn = rvse_cncl_dvsn;
      this.rvse_cncl_dvsn_name = rvse_cncl_dvsn_name;
      this.pdno = pdno;
      this.prdt_name = prdt_name;
      this.ft_ord_qty = ft_ord_qty;
      this.ft_ord_unpr3 = ft_ord_unpr3;
      this.ft_ccld_qty = ft_ccld_qty;
      this.ft_ccld_unpr3 = ft_ccld_unpr3;
      this.ft_ccld_amt3 = ft_ccld_amt3;
      this.nccs_qty = nccs_qty;
      this.prcs_stat_name = prcs_stat_name;
      this.rjct_rson = rjct_rson;
      this.ord_tmd = ord_tmd;
      this.tr_mket_name = tr_mket_name;
      this.tr_natn = tr_natn;
      this.tr_natn_name = tr_natn_name;
      this.ovrs_excg_cd = ovrs_excg_cd;
      this.tr_crcy_cd = tr_crcy_cd;
      this.dmst_ord_dt = dmst_ord_dt;
      this.thco_ord_tmd = thco_ord_tmd;
      this.loan_type_cd = loan_type_cd;
      this.mdia_dvsn_name = mdia_dvsn_name;
      this.loan_dt = loan_dt;
      this.rjct_rson_name = rjct_rson_name;
      this.usa_amk_exts_rqst_yn = usa_amk_exts_rqst_yn;
    }

    public String getOrd_dt() {
      return ord_dt;
    }

    public String getOrd_gno_brno() {
      return ord_gno_brno;
    }

    public String getOdno() {
      return odno;
    }

    public String getOrgn_odno() {
      return orgn_odno;
    }

    public String getSll_buy_dvsn_cd() {
      return sll_buy_dvsn_cd;
    }

    public String getSll_buy_dvsn_cd_name() {
      return sll_buy_dvsn_cd_name;
    }

    public String getRvse_cncl_dvsn() {
      return rvse_cncl_dvsn;
    }

    public String getRvse_cncl_dvsn_name() {
      return rvse_cncl_dvsn_name;
    }

    public String getPdno() {
      return pdno;
    }

    public String getPrdt_name() {
      return prdt_name;
    }

    public String getFt_ord_qty() {
      return ft_ord_qty;
    }

    public String getFt_ord_unpr3() {
      return ft_ord_unpr3;
    }

    public String getFt_ccld_qty() {
      return ft_ccld_qty;
    }

    public String getFt_ccld_unpr3() {
      return ft_ccld_unpr3;
    }

    public String getFt_ccld_amt3() {
      return ft_ccld_amt3;
    }

    public String getNccs_qty() {
      return nccs_qty;
    }

    public String getPrcs_stat_name() {
      return prcs_stat_name;
    }

    public String getRjct_rson() {
      return rjct_rson;
    }

    public String getOrd_tmd() {
      return ord_tmd;
    }

    public String getTr_mket_name() {
      return tr_mket_name;
    }

    public String getTr_natn() {
      return tr_natn;
    }

    public String getTr_natn_name() {
      return tr_natn_name;
    }

    public String getOvrs_excg_cd() {
      return ovrs_excg_cd;
    }

    public String getTr_crcy_cd() {
      return tr_crcy_cd;
    }

    public String getDmst_ord_dt() {
      return dmst_ord_dt;
    }

    public String getThco_ord_tmd() {
      return thco_ord_tmd;
    }

    public String getLoan_type_cd() {
      return loan_type_cd;
    }

    public String getMdia_dvsn_name() {
      return mdia_dvsn_name;
    }

    public String getLoan_dt() {
      return loan_dt;
    }

    public String getRjct_rson_name() {
      return rjct_rson_name;
    }

    public String getUsa_amk_exts_rqst_yn() {
      return usa_amk_exts_rqst_yn;
    }

    @Override
    public String toString() {
      return "ResBodyOutput [ord_dt=" + ord_dt + ", ord_gno_brno=" + ord_gno_brno + ", odno=" + odno + ", orgn_odno="
          + orgn_odno + ", sll_buy_dvsn_cd=" + sll_buy_dvsn_cd + ", sll_buy_dvsn_cd_name=" + sll_buy_dvsn_cd_name
          + ", rvse_cncl_dvsn=" + rvse_cncl_dvsn + ", rvse_cncl_dvsn_name=" + rvse_cncl_dvsn_name + ", pdno=" + pdno
          + ", prdt_name=" + prdt_name + ", ft_ord_qty=" + ft_ord_qty + ", ft_ord_unpr3=" + ft_ord_unpr3
          + ", ft_ccld_qty=" + ft_ccld_qty + ", ft_ccld_unpr3=" + ft_ccld_unpr3 + ", ft_ccld_amt3=" + ft_ccld_amt3
          + ", nccs_qty=" + nccs_qty + ", prcs_stat_name=" + prcs_stat_name + ", rjct_rson=" + rjct_rson + ", ord_tmd="
          + ord_tmd + ", tr_mket_name=" + tr_mket_name + ", tr_natn=" + tr_natn + ", tr_natn_name=" + tr_natn_name
          + ", ovrs_excg_cd=" + ovrs_excg_cd + ", tr_crcy_cd=" + tr_crcy_cd + ", dmst_ord_dt=" + dmst_ord_dt
          + ", thco_ord_tmd=" + thco_ord_tmd + ", loan_type_cd=" + loan_type_cd + ", mdia_dvsn_name=" + mdia_dvsn_name
          + ", loan_dt=" + loan_dt + ", rjct_rson_name=" + rjct_rson_name + ", usa_amk_exts_rqst_yn="
          + usa_amk_exts_rqst_yn + "]";
    }
  }
}
