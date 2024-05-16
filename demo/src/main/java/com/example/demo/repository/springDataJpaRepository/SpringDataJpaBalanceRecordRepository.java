package com.example.demo.repository.springDataJpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.BalanceRecord;
import com.example.demo.repository.BalanceRecordRepository;

public interface SpringDataJpaBalanceRecordRepository extends JpaRepository<BalanceRecord, Long>, BalanceRecordRepository {

}