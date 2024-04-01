package com.example.demo.kisAPI.interfaces.uapi.domestic_stock.v1.trading;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_daily_ccld_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_daily_ccld_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_daily_ccld_DTO.ResBody;

public interface inquire_daily_ccld_Interface {
  @NonNull ResBody get(@NonNull ReqHeader reqHeader, @NonNull ReqQueryParam reqQueryParam);
}
