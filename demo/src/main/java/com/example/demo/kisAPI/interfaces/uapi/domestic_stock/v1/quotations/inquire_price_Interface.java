package com.example.demo.kisAPI.interfaces.uapi.domestic_stock.v1.quotations;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.quotations.inquire_price_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.quotations.inquire_price_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.quotations.inquire_price_DTO.ResBody;

public interface inquire_price_Interface {
  @NonNull public ResBody get(@NonNull ReqHeader reqHeader, @NonNull ReqQueryParam reqQueryParam);
}
