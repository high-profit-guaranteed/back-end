package com.example.demo.kisAPI.dto.tryitout;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Account;

import lombok.Data;

public class HDFSASP0_DTO {
  @Data
  public static class ReqHeader {
    @NonNull
    private final String approval_key;
    @NonNull
    private final String custtype;
    @NonNull
    private final String tr_type;
    @NonNull
    private final String content_type;

    @NonNull
    public static ReqHeader from(@NonNull Account account, boolean isRegister) {
      String approval_key = account.getApproval_key();
      String tr_type = isRegister ? "1" : "2";
      return new ReqHeader(approval_key, "P", tr_type, "utf-8");
    }
  }

  @Data
  public static class ReqBody {
    @NonNull
    private final String tr_id;
    @NonNull
    private final String tr_key;

    @NonNull
    public static ReqBody from(@NonNull String code, boolean isDay) {
      String tr_key = isDay ? "DNAS"+code : "RBAQ"+code;
      return new ReqBody("HDFSASP0", tr_key);
    }
  }
}
