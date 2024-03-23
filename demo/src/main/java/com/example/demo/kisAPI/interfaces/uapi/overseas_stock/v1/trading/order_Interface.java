package com.example.demo.kisAPI.interfaces.uapi.overseas_stock.v1.trading;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.order_DTO.ReqBody;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.order_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.order_DTO.ResBody;

public interface order_Interface {
  @NonNull public ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody);
}
