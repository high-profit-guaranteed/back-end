package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Member {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String pw;
  private String uid;
  private String memberName;
  private String emailName;
  private String emailDomain;

  public Member() {
  }
  
  public Member(String uid, String emailName, String emailDomain, String pw, String memberName) {
    this.uid = uid;
    this.emailName = emailName;
    this.emailDomain = emailDomain;
    this.pw = pw;
    this.memberName = memberName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getEmailName() {
    return emailName;
  }

  public void setEmailName(String emailName) {
    this.emailName = emailName;
  }

  public String getPw() {
    return pw;
  }

  public void setPw(String pw) {
    this.pw = pw;
  }

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

  public String getEmailDomain() {
    return emailDomain;
  }

  public void setEmailDomain(String emailDomain) {
    this.emailDomain = emailDomain;
  }
}
