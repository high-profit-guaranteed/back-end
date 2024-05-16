package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.TradingRecord;

public interface TradingRecordRepository {
  TradingRecord save(TradingRecord tradingRecord);
  void deleteById(Long id);
  List<TradingRecord> findByAccountId(Long accountId);
  List<TradingRecord> findByAccountIdAndTradeTime(Long accountId, String tradeTime);
  TradingRecord findByOrderNo(String orderNo);
}
