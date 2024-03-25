package com.example.demo.kisAPI.interfaces.uapi.domestic_stock.v1.trading;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO.ReqBody;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO.ResBody;

public interface order_cash_Interface {
  @NonNull public ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody);
}
