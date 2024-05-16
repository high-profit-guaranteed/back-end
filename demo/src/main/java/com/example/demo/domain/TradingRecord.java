package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TradingRecord {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long accountId;
  private Long balanceRecordId;
  private String method;
  private String ticker;
  private int amount;
  private double price;
  private String tradeTime;
  private String orderNo;

  public TradingRecord(Long accountId, String method, String ticker, int amount, double price, String tradeTime, String orderNo) {
    this.accountId = accountId;
    this.balanceRecordId = null;
    this.method = method;
    this.ticker = ticker;
    this.amount = amount;
    this.price = price;
    this.tradeTime = tradeTime;
    this.orderNo = orderNo;
  }
}