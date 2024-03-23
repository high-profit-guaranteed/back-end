package com.example.demo.kisAPI.interfaces.oauth2;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.ReqBody;
import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.ResBody;

public interface Approval_Interface {
  @NonNull public ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody);
}
