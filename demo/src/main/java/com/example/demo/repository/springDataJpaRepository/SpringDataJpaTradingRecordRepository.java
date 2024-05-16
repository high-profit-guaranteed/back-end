package com.example.demo.repository.springDataJpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.TradingRecord;
import com.example.demo.repository.TradingRecordRepository;

public interface SpringDataJpaTradingRecordRepository extends JpaRepository<TradingRecord, Long>, TradingRecordRepository {
  
}
