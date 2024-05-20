package com.example.demo;

import com.example.demo.kisAPI.dto.tryitout.HDFSCNT0_DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WsOverseasRequests {
  private HDFSCNT0_DTO.ReqHeader reqHeader;
  private HDFSCNT0_DTO.ReqBody reqBody;
}