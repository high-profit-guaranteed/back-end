package com.example.demo.domain;

public class Account {
  private Long id;
  private Long memberId;       // 회원 번호
  private int accountNumber;   // 계좌 번호
  private String accountType;  // 계좌 유형 (실거래 계좌, 모의투자 계좌)
  private String accountName;  // 계좌 이름 (개인 설정 가능한 계좌 이름)
  private String APP_KEY;
  private String APP_SECRET;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getMemberId() {
    return memberId;
  }
  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }
  public int getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }
  public String getAccountType() {
    return accountType;
  }
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
  public String getAccountName() {
    return accountName;
  }
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
  public String getAPP_KEY() {
    return APP_KEY;
  }
  public void setAPP_KEY(String APP_KEY) {
    this.APP_KEY = APP_KEY;
  }
  public String getAPP_SECRET() {
    return APP_SECRET;
  }
  public void setAPP_SECRET(String APP_SECRET) {
    this.APP_SECRET = APP_SECRET;
  }
}
