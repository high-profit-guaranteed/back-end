package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
public class ClientWebSocketStompConfig {

  @Bean
  public WebSocketStompClient WebSocketStompClient(WebSocketStompClient webSocketClient,
      StompSessionHandler stompSessionHandler) {

    // client to server message converter
    webSocketClient.setMessageConverter(new MappingJackson2MessageConverter());

    StompHeaders stompHeaders = new StompHeaders();
    stompHeaders.add("host", "karim");

    Object[] urlVariables = {};
    String url = "wss://localhost:8443/ws";
    webSocketClient.connectAsync(url, null, stompHeaders, stompSessionHandler, urlVariables);

    return webSocketClient;
  }

  @Bean
    public WebSocketStompClient webSocketClient() {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        return new WebSocketStompClient(webSocketClient);
    }

  @Bean
  public StompSessionHandler stompSessionHandler() {
    return new ClientWebSocketStompSessionHandler();
  }
}