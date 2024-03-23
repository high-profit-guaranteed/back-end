package com.example.demo.kisAPI.interfaces.oauth2;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.oauth2.tokenP_DTO.ReqBody;
import com.example.demo.kisAPI.dto.oauth2.tokenP_DTO.ResBody;

public interface tokenP_Interface {
  @NonNull public ResBody post(@NonNull ReqBody request);
}