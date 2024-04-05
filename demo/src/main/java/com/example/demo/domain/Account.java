package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
// @NamedQuery(
//   name = "Account.updateAccessToken",
//   query = "update Account a set a.accessToken = :accessToken, a.accessTokenExpired = :accessTokenExpired where a.id = :accountId"
// )
public class Account {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long memberId; // 회원 번호
  private int accountNumber; // 계좌 번호
  private short accountProdCode; // 상품 코드
  private String accountName; // 계좌 이름 (개인 설정 가능한 계좌 이름)
  private boolean isVirtual; // 계좌 유형 (실거래 계좌, 모의투자 계좌)
  private String APP_KEY;
  private String APP_SECRET;

  private String accessToken;
  private String accessTokenExpired;
  private String approval_key;

  public Account() {
  }

  public Account(Long memberId, int accountNumber, short accountProdCode, String accountName, boolean isVirtual, String aPP_KEY,
      String aPP_SECRET) {
    this.memberId = memberId;
    this.accountNumber = accountNumber;
    this.accountProdCode = accountProdCode;
    this.accountName = accountName;
    this.isVirtual = isVirtual;
    APP_KEY = aPP_KEY;
    APP_SECRET = aPP_SECRET;
  }

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

  public short getAccountProdCode() {
    return accountProdCode;
  }

  public void setAccountProdCode(short accountProdCode) {
    this.accountProdCode = accountProdCode;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public boolean isVirtual() {
    return isVirtual;
  }

  public void setAccountType(boolean isVirtual) {
    this.isVirtual = isVirtual;
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

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessTokenExpired() {
    return accessTokenExpired;
  }

  public void setAccessTokenExpired(String accessTokenExpired) {
    this.accessTokenExpired = accessTokenExpired;
  }

  public String getApproval_key() {
    return approval_key;
  }

  public void setApproval_key(String approval_key) {
    this.approval_key = approval_key;
  }
}
