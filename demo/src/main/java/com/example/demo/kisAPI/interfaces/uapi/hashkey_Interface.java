package com.example.demo.kisAPI.interfaces.uapi;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.uapi.hashkey_DTO.ReqBody;
import com.example.demo.kisAPI.dto.uapi.hashkey_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.uapi.hashkey_DTO.ResBody;

public interface hashkey_Interface {
  @NonNull public ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody);
}
