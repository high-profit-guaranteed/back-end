package com.example.demo.service.DTO;

class GetBalanceResBodyOutput2Dto {
  private final String frcr_pchs_amt1;
  private final String ovrs_rlzt_pfls_amt;
  private final String ovrs_tot_pfls;
  private final String rlzt_erng_rt;
  private final String tot_evlu_pfls_amt;
  private final String tot_pftrt;
  private final String frcr_buy_amt_smtl1;
  private final String ovrs_rlzt_pfls_amt2;
  private final String frcr_buy_amt_smtl2;

  public GetBalanceResBodyOutput2Dto(String frcr_pchs_amt1, String ovrs_rlzt_pfls_amt, String ovrs_tot_pfls,
      String rlzt_erng_rt, String tot_evlu_pfls_amt, String tot_pftrt, String frcr_buy_amt_smtl1,
      String ovrs_rlzt_pfls_amt2, String frcr_buy_amt_smtl2) {
    this.frcr_pchs_amt1 = frcr_pchs_amt1;
    this.ovrs_rlzt_pfls_amt = ovrs_rlzt_pfls_amt;
    this.ovrs_tot_pfls = ovrs_tot_pfls;
    this.rlzt_erng_rt = rlzt_erng_rt;
    this.tot_evlu_pfls_amt = tot_evlu_pfls_amt;
    this.tot_pftrt = tot_pftrt;
    this.frcr_buy_amt_smtl1 = frcr_buy_amt_smtl1;
    this.ovrs_rlzt_pfls_amt2 = ovrs_rlzt_pfls_amt2;
    this.frcr_buy_amt_smtl2 = frcr_buy_amt_smtl2;
  }

  public String getFrcr_pchs_amt1() {
    return frcr_pchs_amt1;
  }

  public String getOvrs_rlzt_pfls_amt() {
    return ovrs_rlzt_pfls_amt;
  }

  public String getOvrs_tot_pfls() {
    return ovrs_tot_pfls;
  }

  public String getRlzt_erng_rt() {
    return rlzt_erng_rt;
  }

  public String getTot_evlu_pfls_amt() {
    return tot_evlu_pfls_amt;
  }

  public String getTot_pftrt() {
    return tot_pftrt;
  }

  public String getFrcr_buy_amt_smtl1() {
    return frcr_buy_amt_smtl1;
  }

  public String getOvrs_rlzt_pfls_amt2() {
    return ovrs_rlzt_pfls_amt2;
  }

  public String getFrcr_buy_amt_smtl2() {
    return frcr_buy_amt_smtl2;
  }

  @Override
  public String toString() {
    return "GetBalanceResBodyOutput2Dto [frcr_pchs_amt1=" + frcr_pchs_amt1 + ", ovrs_rlzt_pfls_amt="
        + ovrs_rlzt_pfls_amt + ", ovrs_tot_pfls=" + ovrs_tot_pfls + ", rlzt_erng_rt=" + rlzt_erng_rt
        + ", tot_evlu_pfls_amt=" + tot_evlu_pfls_amt + ", tot_pftrt=" + tot_pftrt + ", frcr_buy_amt_smtl1="
        + frcr_buy_amt_smtl1 + ", ovrs_rlzt_pfls_amt2=" + ovrs_rlzt_pfls_amt2 + ", frcr_buy_amt_smtl2="
        + frcr_buy_amt_smtl2 + "]";
  }

  
}