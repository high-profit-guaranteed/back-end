package com.example.demo.service.DTO;

public class GetBalanceResBodyOutput1Dto {
  private final String cano;
  private final String acnt_prdt_cd;
  private final String prdt_type_cd;
  private final String ovrs_pdno;
  private final String ovrs_item_name;
  private final String frcr_evlu_pfls_amt;
  private final String evlu_pfls_rt;
  private final String pchs_avg_pric;
  private final String ovrs_cblc_qty;
  private final String now_pric2;
  private final String tr_crcy_cd;
  private final String ovrs_excg_cd;
  private final String loan_type_cd;
  private final String loan_dt;
  private final String expd_dt;

  public GetBalanceResBodyOutput1Dto(String cano, String acnt_prdt_cd, String prdt_type_cd, String ovrs_pdno,
      String ovrs_item_name, String frcr_evlu_pfls_amt, String evlu_pfls_rt, String pchs_avg_pric, String ovrs_cblc_qty,
      String now_pric2, String tr_crcy_cd, String ovrs_excg_cd, String loan_type_cd, String loan_dt, String expd_dt) {
    this.cano = cano;
    this.acnt_prdt_cd = acnt_prdt_cd;
    this.prdt_type_cd = prdt_type_cd;
    this.ovrs_pdno = ovrs_pdno;
    this.ovrs_item_name = ovrs_item_name;
    this.frcr_evlu_pfls_amt = frcr_evlu_pfls_amt;
    this.evlu_pfls_rt = evlu_pfls_rt;
    this.pchs_avg_pric = pchs_avg_pric;
    this.ovrs_cblc_qty = ovrs_cblc_qty;
    this.now_pric2 = now_pric2;
    this.tr_crcy_cd = tr_crcy_cd;
    this.ovrs_excg_cd = ovrs_excg_cd;
    this.loan_type_cd = loan_type_cd;
    this.loan_dt = loan_dt;
    this.expd_dt = expd_dt;
  }

  public String getCano() {
    return cano;
  }

  public String getAcnt_prdt_cd() {
    return acnt_prdt_cd;
  }

  public String getPrdt_type_cd() {
    return prdt_type_cd;
  }

  public String getOvrs_pdno() {
    return ovrs_pdno;
  }

  public String getOvrs_item_name() {
    return ovrs_item_name;
  }

  public String getFrcr_evlu_pfls_amt() {
    return frcr_evlu_pfls_amt;
  }

  public String getEvlu_pfls_rt() {
    return evlu_pfls_rt;
  }

  public String getPchs_avg_pric() {
    return pchs_avg_pric;
  }

  public String getOvrs_cblc_qty() {
    return ovrs_cblc_qty;
  }

  public String getNow_pric2() {
    return now_pric2;
  }

  public String getTr_crcy_cd() {
    return tr_crcy_cd;
  }

  public String getOvrs_excg_cd() {
    return ovrs_excg_cd;
  }

  public String getLoan_type_cd() {
    return loan_type_cd;
  }

  public String getLoan_dt() {
    return loan_dt;
  }

  public String getExpd_dt() {
    return expd_dt;
  }

  @Override
  public String toString() {
    return "GetBalanceResBodyOutput1Dto [cano=" + cano + ", acnt_prdt_cd=" + acnt_prdt_cd + ", prdt_type_cd="
        + prdt_type_cd + ", ovrs_pdno=" + ovrs_pdno + ", ovrs_item_name=" + ovrs_item_name + ", frcr_evlu_pfls_amt="
        + frcr_evlu_pfls_amt + ", evlu_pfls_rt=" + evlu_pfls_rt + ", pchs_avg_pric=" + pchs_avg_pric
        + ", ovrs_cblc_qty=" + ovrs_cblc_qty + ", now_pric2=" + now_pric2 + ", tr_crcy_cd=" + tr_crcy_cd
        + ", ovrs_excg_cd=" + ovrs_excg_cd + ", loan_type_cd=" + loan_type_cd + ", loan_dt=" + loan_dt + ", expd_dt="
        + expd_dt + "]";
  }

}