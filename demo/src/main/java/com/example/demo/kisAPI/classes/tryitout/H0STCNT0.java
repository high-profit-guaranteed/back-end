package com.example.demo.kisAPI.classes.tryitout;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ReqBody;
import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ReqHeader;
import com.example.demo.kisAPI.interfaces.tryitout.H0STCNT0_Interface;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSocket
public class H0STCNT0 implements H0STCNT0_Interface {

  @NonNull
  private final String url;
  @NonNull
  private final String uriVariable;

  public H0STCNT0(boolean isVirtual) {
    this.url = isVirtual ? "ws://ops.koreainvestment.com:31000" : "ws://ops.koreainvestment.com:21000";
    this.uriVariable = "tryitout/H0STCNT0";
  }

  @Override
  public void post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody) {

    WebSocketClient client = new StandardWebSocketClient();

    log.info("Connecting to the server");

    String request = "{\"header\":{\"approval_key\":\"" + reqHeader.getApproval_key() +
        "\",\"custtype\":\"" + reqHeader.getCusttype() +
        "\",\"tr_type\":\"" + reqHeader.getTr_type() + "\",\"content-type\":\"" + reqHeader.getContent_type() +
        "\"},\"body\":{\"input\":{\"tr_id\":\"" + reqBody.getTr_id() + "\",\"tr_key\":\"" + reqBody.getTr_key()
        + "\"}}}";

    client.execute(new WebSocketClientHandler(request), this.url, this.uriVariable);
  }

  class WebSocketClientHandler implements WebSocketHandler {
    private final String requestStr;

    public WebSocketClientHandler(String request) {
      super();
      this.requestStr = request;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
      log.info("Sending message: " + requestStr);
      session.sendMessage(new TextMessage(requestStr));
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message)
        throws Exception {
      // log.info("Received message: " + message.getPayload());
      if (message.getPayload().toString().contains("\"tr_id\":\"PINGPONG\"")) {
        session.sendMessage(new TextMessage("Pong"));
      }
      if (message.getPayload().toString().startsWith("0")) {
        List<String> list = new ArrayList<String>();
        for (String str : message.getPayload().toString().split("\\|")) {
          list.add(str);
        }
        String data = list.remove(list.size() - 1);
        list.add("data:");
        for (String str : data.split("\\^")) {
          list.add(" "+str);
        }
        log.info("Received message:\n");
        for (String str : list) {
          log.info(str);
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