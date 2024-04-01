package com.example.demo.kisAPI.interfaces.uapi.overseas_stock.v1.trading;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_ccnl_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_ccnl_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_ccnl_DTO.ResBody;

public interface inquire_ccnl_Interface {
  @NonNull
  public ResBody get(@NonNull ReqHeader reqHeader, @NonNull ReqQueryParam reqQueryParam);
}
