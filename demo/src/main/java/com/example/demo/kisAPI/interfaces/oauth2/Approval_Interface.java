package com.example.demo.kisAPI.interfaces.oauth2;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.Req;
import com.example.demo.kisAPI.dto.oauth2.Approval_DTO.Res;

public interface Approval_Interface {
  @NonNull public Res post(@NonNull Req request);
}