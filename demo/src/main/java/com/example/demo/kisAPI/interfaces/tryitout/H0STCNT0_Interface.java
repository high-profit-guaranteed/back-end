package com.example.demo.kisAPI.interfaces.tryitout;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ReqBody;
import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ResBody;

public interface H0STCNT0_Interface {
  @NonNull ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody);
}
