package com.example.demo.kisAPI.interfaces.tryitout;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ReqBody;
import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ReqHeader;

public interface H0STCNT0_Interface {
  void post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody);
}
