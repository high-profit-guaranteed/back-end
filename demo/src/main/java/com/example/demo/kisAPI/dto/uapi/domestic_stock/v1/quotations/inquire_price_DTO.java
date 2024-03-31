package com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.quotations;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

public class inquire_price_DTO {
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
FHKST01010100 : 주식현재가 시세
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

      return new ReqHeader("application/json; charset=utf-8", "Bearer " + AUTHORIZATION, APP_KEY, APP_SECRET, "FHKST01010100");
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
FID_COND_MRKT_DIV_CODE	FID 조건 시장 분류 코드	String	Y	2	J : 주식, ETF, ETN
W: ELW
FID_INPUT_ISCD	FID 입력 종목코드	String	Y	12	종목번호 (6자리)
ETN의 경우, Q로 시작 (EX. Q500001)
*/
  public static class ReqQueryParam {
    @NonNull
    private final String FID_COND_MRKT_DIV_CODE;
    @NonNull
    private final String FID_INPUT_ISCD;

    public ReqQueryParam(@NonNull String FID_COND_MRKT_DIV_CODE, @NonNull String FID_INPUT_ISCD) {
      this.FID_COND_MRKT_DIV_CODE = FID_COND_MRKT_DIV_CODE;
      this.FID_INPUT_ISCD = FID_INPUT_ISCD;
    }

    @NonNull
    public static ReqQueryParam from(@NonNull String FID_INPUT_ISCD) {
      return new ReqQueryParam("J", FID_INPUT_ISCD);
    }

    public String getFID_COND_MRKT_DIV_CODE() {
      return FID_COND_MRKT_DIV_CODE;
    }

    public String getFID_INPUT_ISCD() {
      return FID_INPUT_ISCD;
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

    @Override
    public String toString() {
      return "ResHeader [content_type=" + content_type + ", tr_id=" + tr_id + ", tr_cont=" + tr_cont + ", gt_uid="
          + gt_uid + "]";
    }
  }

/*
rt_cd	성공 실패 여부	String	Y	1	0 : 성공
0 이외의 값 : 실패
msg_cd	응답코드	String	Y	8	응답코드
msg1	응답메세지	String	Y	80	응답메세지
output	응답상세	Object	Y	null	
-iscd_stat_cls_code	종목 상태 구분 코드	String	Y	3	00 : 그외
51 : 관리종목
52 : 투자위험
53 : 투자경고
54 : 투자주의
55 : 신용가능
57 : 증거금 100%
58 : 거래정지
59 : 단기과열
-marg_rate	증거금 비율	String	Y	12	
-rprs_mrkt_kor_name	대표 시장 한글 명	String	Y	40	
-new_hgpr_lwpr_cls_code	신 고가 저가 구분 코드	String	Y	10	조회하는 종목이 신고/신저에 도달했을 경우에만 조회됨
-bstp_kor_isnm	업종 한글 종목명	String	Y	40	
-temp_stop_yn	임시 정지 여부	String	Y	1	
-oprc_rang_cont_yn	시가 범위 연장 여부	String	Y	1	
-clpr_rang_cont_yn	종가 범위 연장 여부	String	Y	1	
-crdt_able_yn	신용 가능 여부	String	Y	1	
-grmn_rate_cls_code	보증금 비율 구분 코드	String	Y	3	한국투자 증거금비율 (marg_rate 참고)
40 : 20%, 30%, 40%
50 : 50%
60 : 60%
-elw_pblc_yn	ELW 발행 여부	String	Y	1	
-stck_prpr	주식 현재가	String	Y	10	
-prdy_vrss	전일 대비	String	Y	10	
-prdy_vrss_sign	전일 대비 부호	String	Y	1	1 : 상한
2 : 상승
3 : 보합
4 : 하한
5 : 하락
-prdy_ctrt	전일 대비율	String	Y	10	
-acml_tr_pbmn	누적 거래 대금	String	Y	18	
-acml_vol	누적 거래량	String	Y	18	
-prdy_vrss_vol_rate	전일 대비 거래량 비율	String	Y	12	주식현재가 일자별 API 응답값 사용
-stck_oprc	주식 시가	String	Y	10	
-stck_hgpr	주식 최고가	String	Y	10	
-stck_lwpr	주식 최저가	String	Y	10	
-stck_mxpr	주식 상한가	String	Y	10	
-stck_llam	주식 하한가	String	Y	10	
-stck_sdpr	주식 기준가	String	Y	10	
-wghn_avrg_stck_prc	가중 평균 주식 가격	String	Y	21	
-hts_frgn_ehrt	HTS 외국인 소진율	String	Y	82	
-frgn_ntby_qty	외국인 순매수 수량	String	Y	12	
-pgtr_ntby_qty	프로그램매매 순매수 수량	String	Y	18	
-pvt_scnd_dmrs_prc	피벗 2차 디저항 가격	String	Y	10	직원용 데이터
-pvt_frst_dmrs_prc	피벗 1차 디저항 가격	String	Y	10	직원용 데이터
-pvt_pont_val	피벗 포인트 값	String	Y	10	직원용 데이터
-pvt_frst_dmsp_prc	피벗 1차 디지지 가격	String	Y	10	직원용 데이터
-pvt_scnd_dmsp_prc	피벗 2차 디지지 가격	String	Y	10	직원용 데이터
-dmrs_val	디저항 값	String	Y	10	직원용 데이터
-dmsp_val	디지지 값	String	Y	10	직원용 데이터
-cpfn	자본금	String	Y	22	
-rstc_wdth_prc	제한 폭 가격	String	Y	10	
-stck_fcam	주식 액면가	String	Y	11	
-stck_sspr	주식 대용가	String	Y	10	
-aspr_unit	호가단위	String	Y	10	
-hts_deal_qty_unit_val	HTS 매매 수량 단위 값	String	Y	10	
-lstn_stcn	상장 주수	String	Y	18	
-hts_avls	HTS 시가총액	String	Y	18	
-per	PER	String	Y	10	
-pbr	PBR	String	Y	10	
-stac_month	결산 월	String	Y	2	
-vol_tnrt	거래량 회전율	String	Y	10	
-eps	EPS	String	Y	13	
-bps	BPS	String	Y	13	
-d250_hgpr	250일 최고가	String	Y	10	
-d250_hgpr_date	250일 최고가 일자	String	Y	8	
-d250_hgpr_vrss_prpr_rate	250일 최고가 대비 현재가 비율	String	Y	12	
-d250_lwpr	250일 최저가	String	Y	10	
-d250_lwpr_date	250일 최저가 일자	String	Y	8	
-d250_lwpr_vrss_prpr_rate	250일 최저가 대비 현재가 비율	String	Y	12	
-stck_dryy_hgpr	주식 연중 최고가	String	Y	10	
-dryy_hgpr_vrss_prpr_rate	연중 최고가 대비 현재가 비율	String	Y	12	
-dryy_hgpr_date	연중 최고가 일자	String	Y	8	
-stck_dryy_lwpr	주식 연중 최저가	String	Y	10	
-dryy_lwpr_vrss_prpr_rate	연중 최저가 대비 현재가 비율	String	Y	12	
-dryy_lwpr_date	연중 최저가 일자	String	Y	8	
-w52_hgpr	52주일 최고가	String	Y	10	
-w52_hgpr_vrss_prpr_ctrt	52주일 최고가 대비 현재가 대비	String	Y	10	
-w52_hgpr_date	52주일 최고가 일자	String	Y	8	
-w52_lwpr	52주일 최저가	String	Y	10	
-w52_lwpr_vrss_prpr_ctrt	52주일 최저가 대비 현재가 대비	String	Y	10	
-w52_lwpr_date	52주일 최저가 일자	String	Y	8	
-whol_loan_rmnd_rate	전체 융자 잔고 비율	String	Y	12	
-ssts_yn	공매도가능여부	String	Y	1	
-stck_shrn_iscd	주식 단축 종목코드	String	Y	9	
-fcam_cnnm	액면가 통화명	String	Y	20	
-cpfn_cnnm	자본금 통화명	String	Y	20	외국주권은 억으로 떨어지며, 그 외에는 만으로 표시됨
-apprch_rate	접근도	String	Y	13	
-frgn_hldn_qty	외국인 보유 수량	String	Y	18	
-vi_cls_code	VI적용구분코드	String	Y	1	
-ovtm_vi_cls_code	시간외단일가VI적용구분코드	String	Y	1	
-last_ssts_cntg_qty	최종 공매도 체결 수량	String	Y	12	
-invt_caful_yn	투자유의여부	String	Y	1	Y/N
-mrkt_warn_cls_code	시장경고코드	String	Y	2	00 : 없음
01 : 투자주의
02 : 투자경고
03 : 투자위험
-short_over_yn	단기과열여부	String	Y	1	Y/N
-sltr_yn	정리매매여부	String	Y	1	Y/N
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
    private final String iscd_stat_cls_code;
    private final String marg_rate;
    private final String rprs_mrkt_kor_name;
    private final String new_hgpr_lwpr_cls_code;
    private final String bstp_kor_isnm;
    private final String temp_stop_yn;
    private final String oprc_rang_cont_yn;
    private final String clpr_rang_cont_yn;
    private final String crdt_able_yn;
    private final String grmn_rate_cls_code;
    private final String elw_pblc_yn;
    private final String stck_prpr;
    private final String prdy_vrss;
    private final String prdy_vrss_sign;
    private final String prdy_ctrt;
    private final String acml_tr_pbmn;
    private final String acml_vol;
    private final String prdy_vrss_vol_rate;
    private final String stck_oprc;
    private final String stck_hgpr;
    private final String stck_lwpr;
    private final String stck_mxpr;
    private final String stck_llam;
    private final String stck_sdpr;
    private final String wghn_avrg_stck_prc;
    private final String hts_frgn_ehrt;
    private final String frgn_ntby_qty;
    private final String pgtr_ntby_qty;
    private final String pvt_scnd_dmrs_prc;
    private final String pvt_frst_dmrs_prc;
    private final String pvt_pont_val;
    private final String pvt_frst_dmsp_prc;
    private final String pvt_scnd_dmsp_prc;
    private final String dmrs_val;
    private final String dmsp_val;
    private final String cpfn;
    private final String rstc_wdth_prc;
    private final String stck_fcam;
    private final String stck_sspr;
    private final String aspr_unit;
    private final String hts_deal_qty_unit_val;
    private final String lstn_stcn;
    private final String hts_avls;
    private final String per;
    private final String pbr;
    private final String stac_month;
    private final String vol_tnrt;
    private final String eps;
    private final String bps;
    private final String d250_hgpr;
    private final String d250_hgpr_date;
    private final String d250_hgpr_vrss_prpr_rate;
    private final String d250_lwpr;
    private final String d250_lwpr_date;
    private final String d250_lwpr_vrss_prpr_rate;
    private final String stck_dryy_hgpr;
    private final String dryy_hgpr_vrss_prpr_rate;
    private final String dryy_hgpr_date;
    private final String stck_dryy_lwpr;
    private final String dryy_lwpr_vrss_prpr_rate;
    private final String dryy_lwpr_date;
    private final String w52_hgpr;
    private final String w52_hgpr_vrss_prpr_ctrt;
    private final String w52_hgpr_date;
    private final String w52_lwpr;
    private final String w52_lwpr_vrss_prpr_ctrt;
    private final String w52_lwpr_date;
    private final String whol_loan_rmnd_rate;
    private final String ssts_yn;
    private final String stck_shrn_iscd;
    private final String fcam_cnnm;
    private final String cpfn_cnnm;
    private final String apprch_rate;
    private final String frgn_hldn_qty;
    private final String vi_cls_code;
    private final String ovtm_vi_cls_code;
    private final String last_ssts_cntg_qty;
    private final String invt_caful_yn;
    private final String mrkt_warn_cls_code;
    private final String short_over_yn;
    private final String sltr_yn;

    public ResBodyOutput(String iscd_stat_cls_code, String marg_rate, String rprs_mrkt_kor_name,
        String new_hgpr_lwpr_cls_code, String bstp_kor_isnm, String temp_stop_yn, String oprc_rang_cont_yn,
        String clpr_rang_cont_yn, String crdt_able_yn, String grmn_rate_cls_code, String elw_pblc_yn, String stck_prpr,
        String prdy_vrss, String prdy_vrss_sign, String prdy_ctrt, String acml_tr_pbmn, String acml_vol,
        String prdy_vrss_vol_rate, String stck_oprc, String stck_hgpr, String stck_lwpr, String stck_mxpr,
        String stck_llam, String stck_sdpr, String wghn_avrg_stck_prc, String hts_frgn_ehrt, String frgn_ntby_qty,
        String pgtr_ntby_qty, String pvt_scnd_dmrs_prc, String pvt_frst_dmrs_prc, String pvt_pont_val,
        String pvt_frst_dmsp_prc, String pvt_scnd_dmsp_prc, String dmrs_val, String dmsp_val, String cpfn,
        String rstc_wdth_prc, String stck_fcam, String stck_sspr, String aspr_unit, String hts_deal_qty_unit_val,
        String lstn_stcn, String hts_avls, String per, String pbr, String stac_month, String vol_tnrt, String eps,
        String bps, String d250_hgpr, String d250_hgpr_date, String d250_hgpr_vrss_prpr_rate, String d250_lwpr,
        String d250_lwpr_date, String d250_lwpr_vrss_prpr_rate, String stck_dryy_hgpr, String dryy_hgpr_vrss_prpr_rate,
        String dryy_hgpr_date, String stck_dryy_lwpr, String dryy_lwpr_vrss_prpr_rate, String dryy_lwpr_date,
        String w52_hgpr, String w52_hgpr_vrss_prpr_ctrt, String w52_hgpr_date, String w52_lwpr,
        String w52_lwpr_vrss_prpr_ctrt, String w52_lwpr_date, String whol_loan_rmnd_rate, String ssts_yn,
        String stck_shrn_iscd, String fcam_cnnm, String cpfn_cnnm, String apprch_rate, String frgn_hldn_qty,
        String vi_cls_code, String ovtm_vi_cls_code, String last_ssts_cntg_qty, String invt_caful_yn,
        String mrkt_warn_cls_code, String short_over_yn, String sltr_yn) {
      this.iscd_stat_cls_code = iscd_stat_cls_code;
      this.marg_rate = marg_rate;
      this.rprs_mrkt_kor_name = rprs_mrkt_kor_name;
      this.new_hgpr_lwpr_cls_code = new_hgpr_lwpr_cls_code;
      this.bstp_kor_isnm = bstp_kor_isnm;
      this.temp_stop_yn = temp_stop_yn;
      this.oprc_rang_cont_yn = oprc_rang_cont_yn;
      this.clpr_rang_cont_yn = clpr_rang_cont_yn;
      this.crdt_able_yn = crdt_able_yn;
      this.grmn_rate_cls_code = grmn_rate_cls_code;
      this.elw_pblc_yn = elw_pblc_yn;
      this.stck_prpr = stck_prpr;
      this.prdy_vrss = prdy_vrss;
      this.prdy_vrss_sign = prdy_vrss_sign;
      this.prdy_ctrt = prdy_ctrt;
      this.acml_tr_pbmn = acml_tr_pbmn;
      this.acml_vol = acml_vol;
      this.prdy_vrss_vol_rate = prdy_vrss_vol_rate;
      this.stck_oprc = stck_oprc;
      this.stck_hgpr = stck_hgpr;
      this.stck_lwpr = stck_lwpr;
      this.stck_mxpr = stck_mxpr;
      this.stck_llam = stck_llam;
      this.stck_sdpr = stck_sdpr;
      this.wghn_avrg_stck_prc = wghn_avrg_stck_prc;
      this.hts_frgn_ehrt = hts_frgn_ehrt;
      this.frgn_ntby_qty = frgn_ntby_qty;
      this.pgtr_ntby_qty = pgtr_ntby_qty;
      this.pvt_scnd_dmrs_prc = pvt_scnd_dmrs_prc;
      this.pvt_frst_dmrs_prc = pvt_frst_dmrs_prc;
      this.pvt_pont_val = pvt_pont_val;
      this.pvt_frst_dmsp_prc = pvt_frst_dmsp_prc;
      this.pvt_scnd_dmsp_prc = pvt_scnd_dmsp_prc;
      this.dmrs_val = dmrs_val;
      this.dmsp_val = dmsp_val;
      this.cpfn = cpfn;
      this.rstc_wdth_prc = rstc_wdth_prc;
      this.stck_fcam = stck_fcam;
      this.stck_sspr = stck_sspr;
      this.aspr_unit = aspr_unit;
      this.hts_deal_qty_unit_val = hts_deal_qty_unit_val;
      this.lstn_stcn = lstn_stcn;
      this.hts_avls = hts_avls;
      this.per = per;
      this.pbr = pbr;
      this.stac_month = stac_month;
      this.vol_tnrt = vol_tnrt;
      this.eps = eps;
      this.bps = bps;
      this.d250_hgpr = d250_hgpr;
      this.d250_hgpr_date = d250_hgpr_date;
      this.d250_hgpr_vrss_prpr_rate = d250_hgpr_vrss_prpr_rate;
      this.d250_lwpr = d250_lwpr;
      this.d250_lwpr_date = d250_lwpr_date;
      this.d250_lwpr_vrss_prpr_rate = d250_lwpr_vrss_prpr_rate;
      this.stck_dryy_hgpr = stck_dryy_hgpr;
      this.dryy_hgpr_vrss_prpr_rate = dryy_hgpr_vrss_prpr_rate;
      this.dryy_hgpr_date = dryy_hgpr_date;
      this.stck_dryy_lwpr = stck_dryy_lwpr;
      this.dryy_lwpr_vrss_prpr_rate = dryy_lwpr_vrss_prpr_rate;
      this.dryy_lwpr_date = dryy_lwpr_date;
      this.w52_hgpr = w52_hgpr;
      this.w52_hgpr_vrss_prpr_ctrt = w52_hgpr_vrss_prpr_ctrt;
      this.w52_hgpr_date = w52_hgpr_date;
      this.w52_lwpr = w52_lwpr;
      this.w52_lwpr_vrss_prpr_ctrt = w52_lwpr_vrss_prpr_ctrt;
      this.w52_lwpr_date = w52_lwpr_date;
      this.whol_loan_rmnd_rate = whol_loan_rmnd_rate;
      this.ssts_yn = ssts_yn;
      this.stck_shrn_iscd = stck_shrn_iscd;
      this.fcam_cnnm = fcam_cnnm;
      this.cpfn_cnnm = cpfn_cnnm;
      this.apprch_rate = apprch_rate;
      this.frgn_hldn_qty = frgn_hldn_qty;
      this.vi_cls_code = vi_cls_code;
      this.ovtm_vi_cls_code = ovtm_vi_cls_code;
      this.last_ssts_cntg_qty = last_ssts_cntg_qty;
      this.invt_caful_yn = invt_caful_yn;
      this.mrkt_warn_cls_code = mrkt_warn_cls_code;
      this.short_over_yn = short_over_yn;
      this.sltr_yn = sltr_yn;
    }

    public String getIscd_stat_cls_code() {
      return iscd_stat_cls_code;
    }

    public String getMarg_rate() {
      return marg_rate;
    }

    public String getRprs_mrkt_kor_name() {
      return rprs_mrkt_kor_name;
    }

    public String getNew_hgpr_lwpr_cls_code() {
      return new_hgpr_lwpr_cls_code;
    }

    public String getBstp_kor_isnm() {
      return bstp_kor_isnm;
    }

    public String getTemp_stop_yn() {
      return temp_stop_yn;
    }

    public String getOprc_rang_cont_yn() {
      return oprc_rang_cont_yn;
    }

    public String getClpr_rang_cont_yn() {
      return clpr_rang_cont_yn;
    }

    public String getCrdt_able_yn() {
      return crdt_able_yn;
    }

    public String getGrmn_rate_cls_code() {
      return grmn_rate_cls_code;
    }

    public String getElw_pblc_yn() {
      return elw_pblc_yn;
    }

    public String getStck_prpr() {
      return stck_prpr;
    }

    public String getPrdy_vrss() {
      return prdy_vrss;
    }

    public String getPrdy_vrss_sign() {
      return prdy_vrss_sign;
    }

    public String getPrdy_ctrt() {
      return prdy_ctrt;
    }

    public String getAcml_tr_pbmn() {
      return acml_tr_pbmn;
    }

    public String getAcml_vol() {
      return acml_vol;
    }

    public String getPrdy_vrss_vol_rate() {
      return prdy_vrss_vol_rate;
    }

    public String getStck_oprc() {
      return stck_oprc;
    }

    public String getStck_hgpr() {
      return stck_hgpr;
    }

    public String getStck_lwpr() {
      return stck_lwpr;
    }

    public String getStck_mxpr() {
      return stck_mxpr;
    }

    public String getStck_llam() {
      return stck_llam;
    }

    public String getStck_sdpr() {
      return stck_sdpr;
    }

    public String getWghn_avrg_stck_prc() {
      return wghn_avrg_stck_prc;
    }

    public String getHts_frgn_ehrt() {
      return hts_frgn_ehrt;
    }

    public String getFrgn_ntby_qty() {
      return frgn_ntby_qty;
    }

    public String getPgtr_ntby_qty() {
      return pgtr_ntby_qty;
    }

    public String getPvt_scnd_dmrs_prc() {
      return pvt_scnd_dmrs_prc;
    }

    public String getPvt_frst_dmrs_prc() {
      return pvt_frst_dmrs_prc;
    }

    public String getPvt_pont_val() {
      return pvt_pont_val;
    }

    public String getPvt_frst_dmsp_prc() {
      return pvt_frst_dmsp_prc;
    }

    public String getPvt_scnd_dmsp_prc() {
      return pvt_scnd_dmsp_prc;
    }

    public String getDmrs_val() {
      return dmrs_val;
    }

    public String getDmsp_val() {
      return dmsp_val;
    }

    public String getCpfn() {
      return cpfn;
    }

    public String getRstc_wdth_prc() {
      return rstc_wdth_prc;
    }

    public String getStck_fcam() {
      return stck_fcam;
    }

    public String getStck_sspr() {
      return stck_sspr;
    }

    public String getAspr_unit() {
      return aspr_unit;
    }

    public String getHts_deal_qty_unit_val() {
      return hts_deal_qty_unit_val;
    }

    public String getLstn_stcn() {
      return lstn_stcn;
    }

    public String getHts_avls() {
      return hts_avls;
    }

    public String getPer() {
      return per;
    }

    public String getPbr() {
      return pbr;
    }

    public String getStac_month() {
      return stac_month;
    }

    public String getVol_tnrt() {
      return vol_tnrt;
    }

    public String getEps() {
      return eps;
    }

    public String getBps() {
      return bps;
    }

    public String getD250_hgpr() {
      return d250_hgpr;
    }

    public String getD250_hgpr_date() {
      return d250_hgpr_date;
    }

    public String getD250_hgpr_vrss_prpr_rate() {
      return d250_hgpr_vrss_prpr_rate;
    }

    public String getD250_lwpr() {
      return d250_lwpr;
    }

    public String getD250_lwpr_date() {
      return d250_lwpr_date;
    }

    public String getD250_lwpr_vrss_prpr_rate() {
      return d250_lwpr_vrss_prpr_rate;
    }

    public String getStck_dryy_hgpr() {
      return stck_dryy_hgpr;
    }

    public String getDryy_hgpr_vrss_prpr_rate() {
      return dryy_hgpr_vrss_prpr_rate;
    }

    public String getDryy_hgpr_date() {
      return dryy_hgpr_date;
    }

    public String getStck_dryy_lwpr() {
      return stck_dryy_lwpr;
    }

    public String getDryy_lwpr_vrss_prpr_rate() {
      return dryy_lwpr_vrss_prpr_rate;
    }

    public String getDryy_lwpr_date() {
      return dryy_lwpr_date;
    }

    public String getW52_hgpr() {
      return w52_hgpr;
    }

    public String getW52_hgpr_vrss_prpr_ctrt() {
      return w52_hgpr_vrss_prpr_ctrt;
    }

    public String getW52_hgpr_date() {
      return w52_hgpr_date;
    }

    public String getW52_lwpr() {
      return w52_lwpr;
    }

    public String getW52_lwpr_vrss_prpr_ctrt() {
      return w52_lwpr_vrss_prpr_ctrt;
    }

    public String getW52_lwpr_date() {
      return w52_lwpr_date;
    }

    public String getWhol_loan_rmnd_rate() {
      return whol_loan_rmnd_rate;
    }

    public String getSsts_yn() {
      return ssts_yn;
    }

    public String getStck_shrn_iscd() {
      return stck_shrn_iscd;
    }

    public String getFcam_cnnm() {
      return fcam_cnnm;
    }

    public String getCpfn_cnnm() {
      return cpfn_cnnm;
    }

    public String getApprch_rate() {
      return apprch_rate;
    }

    public String getFrgn_hldn_qty() {
      return frgn_hldn_qty;
    }

    public String getVi_cls_code() {
      return vi_cls_code;
    }

    public String getOvtm_vi_cls_code() {
      return ovtm_vi_cls_code;
    }

    public String getLast_ssts_cntg_qty() {
      return last_ssts_cntg_qty;
    }

    public String getInvt_caful_yn() {
      return invt_caful_yn;
    }

    public String getMrkt_warn_cls_code() {
      return mrkt_warn_cls_code;
    }

    public String getShort_over_yn() {
      return short_over_yn;
    }

    public String getSltr_yn() {
      return sltr_yn;
    }

    @Override
    public String toString() {
      return "ResBodyOutput [iscd_stat_cls_code=" + iscd_stat_cls_code + ", marg_rate=" + marg_rate
          + ", rprs_mrkt_kor_name=" + rprs_mrkt_kor_name + ", new_hgpr_lwpr_cls_code=" + new_hgpr_lwpr_cls_code
          + ", bstp_kor_isnm=" + bstp_kor_isnm + ", temp_stop_yn=" + temp_stop_yn + ", oprc_rang_cont_yn="
          + oprc_rang_cont_yn + ", clpr_rang_cont_yn=" + clpr_rang_cont_yn + ", crdt_able_yn=" + crdt_able_yn
          + ", grmn_rate_cls_code=" + grmn_rate_cls_code + ", elw_pblc_yn=" + elw_pblc_yn + ", stck_prpr=" + stck_prpr
          + ", prdy_vrss=" + prdy_vrss + ", prdy_vrss_sign=" + prdy_vrss_sign + ", prdy_ctrt=" + prdy_ctrt
          + ", acml_tr_pbmn=" + acml_tr_pbmn + ", acml_vol=" + acml_vol + ", prdy_vrss_vol_rate=" + prdy_vrss_vol_rate
          + ", stck_oprc=" + stck_oprc + ", stck_hgpr=" + stck_hgpr + ", stck_lwpr=" + stck_lwpr + ", stck_mxpr="
          + stck_mxpr + ", stck_llam=" + stck_llam + ", stck_sdpr=" + stck_sdpr + ", wghn_avrg_stck_prc="
          + wghn_avrg_stck_prc + ", hts_frgn_ehrt=" + hts_frgn_ehrt + ", frgn_ntby_qty=" + frgn_ntby_qty
          + ", pgtr_ntby_qty=" + pgtr_ntby_qty + ", pvt_scnd_dmrs_prc=" + pvt_scnd_dmrs_prc + ", pvt_frst_dmrs_prc="
          + pvt_frst_dmrs_prc + ", pvt_pont_val=" + pvt_pont_val + ", pvt_frst_dmsp_prc=" + pvt_frst_dmsp_prc
          + ", pvt_scnd_dmsp_prc=" + pvt_scnd_dmsp_prc + ", dmrs_val=" + dmrs_val + ", dmsp_val=" + dmsp_val + ", cpfn="
          + cpfn + ", rstc_wdth_prc=" + rstc_wdth_prc + ", stck_fcam=" + stck_fcam + ", stck_sspr=" + stck_sspr
          + ", aspr_unit=" + aspr_unit + ", hts_deal_qty_unit_val=" + hts_deal_qty_unit_val + ", lstn_stcn=" + lstn_stcn
          + ", hts_avls=" + hts_avls + ", per=" + per + ", pbr=" + pbr + ", stac_month=" + stac_month + ", vol_tnrt="
          + vol_tnrt + ", eps=" + eps + ", bps=" + bps + ", d250_hgpr=" + d250_hgpr + ", d250_hgpr_date="
          + d250_hgpr_date + ", d250_hgpr_vrss_prpr_rate=" + d250_hgpr_vrss_prpr_rate + ", d250_lwpr=" + d250_lwpr
          + ", d250_lwpr_date=" + d250_lwpr_date + ", d250_lwpr_vrss_prpr_rate=" + d250_lwpr_vrss_prpr_rate
          + ", stck_dryy_hgpr=" + stck_dryy_hgpr + ", dryy_hgpr_vrss_prpr_rate=" + dryy_hgpr_vrss_prpr_rate
          + ", dryy_hgpr_date=" + dryy_hgpr_date + ", stck_dryy_lwpr=" + stck_dryy_lwpr + ", dryy_lwpr_vrss_prpr_rate="
          + dryy_lwpr_vrss_prpr_rate + ", dryy_lwpr_date=" + dryy_lwpr_date + ", w52_hgpr=" + w52_hgpr
          + ", w52_hgpr_vrss_prpr_ctrt=" + w52_hgpr_vrss_prpr_ctrt + ", w52_hgpr_date=" + w52_hgpr_date + ", w52_lwpr="
          + w52_lwpr + ", w52_lwpr_vrss_prpr_ctrt=" + w52_lwpr_vrss_prpr_ctrt + ", w52_lwpr_date=" + w52_lwpr_date
          + ", whol_loan_rmnd_rate=" + whol_loan_rmnd_rate + ", ssts_yn=" + ssts_yn + ", stck_shrn_iscd="
          + stck_shrn_iscd + ", fcam_cnnm=" + fcam_cnnm + ", cpfn_cnnm=" + cpfn_cnnm + ", apprch_rate=" + apprch_rate
          + ", frgn_hldn_qty=" + frgn_hldn_qty + ", vi_cls_code=" + vi_cls_code + ", ovtm_vi_cls_code="
          + ovtm_vi_cls_code + ", last_ssts_cntg_qty=" + last_ssts_cntg_qty + ", invt_caful_yn=" + invt_caful_yn
          + ", mrkt_warn_cls_code=" + mrkt_warn_cls_code + ", short_over_yn=" + short_over_yn + ", sltr_yn=" + sltr_yn
          + "]";
    }
  }
}
