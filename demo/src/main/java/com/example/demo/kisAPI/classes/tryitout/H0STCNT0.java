package com.example.demo.kisAPI.classes.tryitout;

import org.springframework.lang.NonNull;

import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ReqBody;
import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ReqHeader;
import com.example.demo.kisAPI.dto.tryitout.H0STCNT0_DTO.ResBody;
import com.example.demo.kisAPI.interfaces.tryitout.H0STCNT0_Interface;

public class H0STCNT0 implements H0STCNT0_Interface {

  @Override
  @NonNull
  public ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'post'");
  }

  // @Configuration
  // @EnableWebSocket
  // @RequiredArgsConstructor
  // public class WebSocketConfig implements WebSocketConfigurer {

  //   @NonNull
  //   private final WebSocketHandler webSocketHandler;

  //   @Override
  //   public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
  //     registry.addHandler(webSocketHandler, "/ws");
  //   }
  // }

  // @Component
  // public class WebSocketHandler extends TextWebSocketHandler {

  //   private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

  //   @Override
  //   public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
  //     CLIENTS.put(session.getId(), session);
  //   }

  //   @Override
  //   public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
  //     CLIENTS.remove(session.getId());
  //   }

  //   @Override
  //   protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
  //     String id = session.getId(); // 메시지를 보낸 아이디
  //     CLIENTS.entrySet().forEach(arg -> {
  //       if (!arg.getKey().equals(id)) { // 같은 아이디가 아니면 메시지를 전달합니다.
  //         try {
  //           arg.getValue().sendMessage(message);
  //         } catch (IOException e) {
  //           e.printStackTrace();
  //         }
  //       }
  //     });
  //   }
  // }

  // @Override
  // @NonNull
  // public ResBody post(@NonNull ReqHeader reqHeader, @NonNull ReqBody reqBody) {

  // }

}
