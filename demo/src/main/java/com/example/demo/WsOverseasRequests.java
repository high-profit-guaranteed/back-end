package com.example.demo;

import com.example.demo.kisAPI.dto.tryitout.HDFSASP0_DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WsOverseasRequests {
  private HDFSASP0_DTO.ReqHeader reqHeader;
  private HDFSASP0_DTO.ReqBody reqBody;
}