package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.TradingRecord;
import com.example.demo.repository.TradingRecordRepository;

@Service
public class TradingRecordService {
  private final TradingRecordRepository tradingRecordRepository;
  
  TradingRecordService(TradingRecordRepository tradingRecordRepository) {
    this.tradingRecordRepository = tradingRecordRepository;
  }

  public TradingRecord save(TradingRecord tradingRecord) {
    tradingRecord = tradingRecordRepository.save(tradingRecord);
    return tradingRecord;
  }

  public void deleteById(Long id) {
    tradingRecordRepository.deleteById(id);
  }

  public List<TradingRecord> findByAccountId(Long accountId) {
    return tradingRecordRepository.findByAccountId(accountId);
  }

  public List<TradingRecord> findByAccountIdAndTradeTime(Long accountId, String tradeTime) {
    return tradingRecordRepository.findByAccountIdAndTradeTime(accountId, tradeTime);
  }

  public TradingRecord findByOrderNo(String orderNo) {
    return tradingRecordRepository.findByOrderNo(orderNo);
  }
}
