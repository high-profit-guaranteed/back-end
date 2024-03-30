package com.example.demo.kisAPI.interfaces.uapi.overseas_price.v1.quotations;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations.price_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations.price_DTO.ReqQueryParam;
import com.example.demo.kisAPI.dto.uapi.overseas_price.v1.quotations.price_DTO.ResBody;

public interface price_Interface {
  @NonNull public ResBody get(@NonNull ReqHeader reqHeader, @NonNull ReqQueryParam reqQueryParam);
}
