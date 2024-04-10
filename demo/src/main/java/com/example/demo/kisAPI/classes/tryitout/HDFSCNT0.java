package com.example.demo.kisAPI.classes.tryitout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.example.demo.kisAPI.dto.tryitout.HDFSCNT0_DTO;
import com.example.demo.kisAPI.dto.tryitout.HDFSCNT0_DTO.ReqBody;
import com.example.demo.kisAPI.dto.tryitout.HDFSCNT0_DTO.ReqHeader;
import com.example.demo.kisAPI.interfaces.tryitout.HDFSCNT0_Interface;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSocket
public class HDFSCNT0 implements HDFSCNT0_Interface {

  @NonNull
  private final String url;
  @NonNull
  private final String uriVariable;

  public HDFSCNT0() {
    this.url = "ws://ops.koreainvestment.com:21000";
    this.uriVariable = "tryitout/HDFSCNT0";
  }

  @Override
  public void post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody) {

    WebSocketClient client = new StandardWebSocketClient();

    log.info("Connecting to the server");

    String request = "{\"header\":{\"approval_key\":\"" + reqHeader.getApproval_key() + "\",\"tr_type\":\""
        + reqHeader.getTr_type() + "\",\"custtype\":\"" + reqHeader.getCusttype() + "\",\"content-type\":\""
        + reqHeader.getContent_type() + "\"},\"body\":{\"input\":{\"tr_id\":\"" + reqBody.getTr_id()
        + "\",\"tr_key\":\"" + reqBody.getTr_key() + "\"}}}";

    client.execute(new WebSocketClientHandler(request), this.url, this.uriVariable);
  }

  class WebSocketClientHandler implements WebSocketHandler {
    private final String requestStr;

    public WebSocketClientHandler(String request) {
      super();
      this.requestStr = request;
    }

    // 연결 성공
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
      log.info("Sending message: " + requestStr);
      // 세션에 메세지 전송
      session.sendMessage(new TextMessage(requestStr));
    }

    // 메세지 수신
    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message)
        throws Exception {

      // 수신 메세지 변환
      String response = message.getPayload().toString();

      // log.info(response);

      // 핑퐁
      if (response.contains("\"tr_id\":\"PINGPONG\"")) {
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        session.sendMessage(new TextMessage("{\"header\":{\"tr_id\":\"PINGPONG\",\"datetime\":\"" + nowStr + "\"}}"));
      }

      // 데이터 수신
      if (response.startsWith("0")) {
        // log.info(response);
        String list[] = new String[4];
        list = response.split("\\|");

        log.info("=========Received message:==========");
        // for (String str : list) {
        // log.info(str);
        // }

        String data = list[3];

        int dataSize = Integer.parseInt(list[2]);

        String dataList[] = new String[26 * dataSize];
        dataList = data.split("\\^");

        for (int i = 0; i < dataSize; i++) {
          int index = i * 26;
          HDFSCNT0_DTO.ResBody resBody = new HDFSCNT0_DTO.ResBody(dataList[0 + index], dataList[1 + index],
              dataList[2 + index], dataList[3 + index], dataList[4 + index], dataList[5 + index], dataList[6 + index],
              dataList[7 + index], dataList[8 + index], dataList[9 + index], dataList[10 + index], dataList[11 + index],
              dataList[12 + index], dataList[13 + index], dataList[14 + index], dataList[15 + index],
              dataList[16 + index], dataList[17 + index], dataList[18 + index], dataList[19 + index],
              dataList[20 + index], dataList[21 + index], dataList[22 + index], dataList[23 + index],
              dataList[24 + index], dataList[25 + index]);

          log.info("=[Data " + String.valueOf(i) + "]===========================");
          log.info(resBody.toString());
          log.info("====================================\n");
        }
      }
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
      log.error("Transport error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus)
        throws Exception {
      log.info("Connection closed: " + closeStatus.getCode());
    }

    @Override
    public boolean supportsPartialMessages() {
      return false;
    }
  }
}