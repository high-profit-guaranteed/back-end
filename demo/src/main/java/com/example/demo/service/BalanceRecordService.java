package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.BalanceRecord;
import com.example.demo.repository.BalanceRecordRepository;

@Service
public class BalanceRecordService {
  private final BalanceRecordRepository balanceRecordRepository;

  public BalanceRecordService(BalanceRecordRepository balanceRecordRepository) {
    this.balanceRecordRepository = balanceRecordRepository;
  }

  public BalanceRecord save(BalanceRecord balanceRecord) {
    balanceRecord = balanceRecordRepository.save(balanceRecord);
    return balanceRecord;
  }

  public BalanceRecord findByAccountIdAndRecordDate(Long accountId, String recordDate) {
    return balanceRecordRepository.findByAccountIdAndRecordDate(accountId, recordDate);
  }

  public void updateByAccountIdAndRecordDate(Long accountId, String recordDate, Double balance) {
    BalanceRecord balanceRecord = balanceRecordRepository.findByAccountIdAndRecordDate(accountId, recordDate);
    balanceRecord.setBalance(balance);
    balanceRecordRepository.save(balanceRecord);
  }

  public List<BalanceRecord> findByAccountId(Long accountId) {
    return balanceRecordRepository.findByAccountId(accountId);
  }
}
