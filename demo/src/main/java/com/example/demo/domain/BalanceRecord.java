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
public class BalanceRecord {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long accountId;
  private Double balance;
  private String recordDate;

  public BalanceRecord(Long accountId, Double balance, String recordDate) {
    this.accountId = accountId;
    this.balance = balance;
    this.recordDate = recordDate;
  }
}