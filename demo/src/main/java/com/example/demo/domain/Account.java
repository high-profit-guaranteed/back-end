package com.example.demo.domain;

public class Account {
  private Long id;
  private int account_number;
  private String account_type;
  private String account_name;
  private String APP_KEY;
  private String APP_SECRET;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public int getAccount_number() {
    return account_number;
  }
  public void setAccount_number(int account_number) {
    this.account_number = account_number;
  }
  public String getAccount_type() {
    return account_type;
  }
  public void setAccount_type(String account_type) {
    this.account_type = account_type;
  }
  public String getAccount_name() {
    return account_name;
  }
  public void setAccount_name(String account_name) {
    this.account_name = account_name;
  }
  public String getAPP_KEY() {
    return APP_KEY;
  }
  public void setAPP_KEY(String aPP_KEY) {
    APP_KEY = aPP_KEY;
  }
  public String getAPP_SECRET() {
    return APP_SECRET;
  }
  public void setAPP_SECRET(String aPP_SECRET) {
    APP_SECRET = aPP_SECRET;
  }

  
}
