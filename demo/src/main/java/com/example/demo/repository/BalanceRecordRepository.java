package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.BalanceRecord;

public interface BalanceRecordRepository {
  BalanceRecord save(BalanceRecord balanceRecord);
  BalanceRecord findByAccountIdAndRecordDate(Long accountId, String recordDate);
  List<BalanceRecord> findByAccountId(Long accountId);
}
