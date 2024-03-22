package com.example.demo.service.DTO;

import java.util.List;

public class GetBalanceResBodyDto {
  private final String rt_cd;
  private final String msg_cd;
  private final String msg1;
  private final String ctx_area_fk200;
  private final String ctx_area_nk200;
  private final List<GetBalanceResBodyOutput1Dto> output1;
  private final GetBalanceResBodyOutput2Dto output2;

  public GetBalanceResBodyDto(String rt_cd, String msg_cd, String msg1, String ctx_area_fk200, String ctx_area_nk200,
      List<GetBalanceResBodyOutput1Dto> output1, GetBalanceResBodyOutput2Dto output2) {
    this.rt_cd = rt_cd;
    this.msg_cd = msg_cd;
    this.msg1 = msg1;
    this.ctx_area_fk200 = ctx_area_fk200;
    this.ctx_area_nk200 = ctx_area_nk200;
    this.output1 = output1;
    this.output2 = output2;
  }

  public String getRt_cd() {
    return rt_cd;
  }

  public String getMsg_cd() {
    return msg_cd;
  }

  public String getMsg1() {
    return msg1;
  }

  public String getCtx_area_fk200() {
    return ctx_area_fk200;
  }

  public String getCtx_area_nk200() {
    return ctx_area_nk200;
  }

  public List<GetBalanceResBodyOutput1Dto> getOutput1() {
    return output1;
  }

  public GetBalanceResBodyOutput2Dto getOutput2() {
    return output2;
  }

  @Override
  public String toString() {
    return "GetBalanceResBodyDto [rt_cd=" + rt_cd + ", msg_cd=" + msg_cd + ", msg1=" + msg1 + ", ctx_area_fk200="
        + ctx_area_fk200 + ", ctx_area_nk200=" + ctx_area_nk200 + ", output1=" + output1 + ", output2=" + output2 + "]";
  }

  
}