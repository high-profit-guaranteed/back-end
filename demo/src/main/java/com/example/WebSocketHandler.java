// package com.example;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.web.socket.TextMessage;
// import org.springframework.web.socket.WebSocketSession;
// import org.springframework.web.socket.handler.TextWebSocketHandler;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// public class WebSocketHandler extends TextWebSocketHandler {
//   Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();

//   @Override
//   public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//     log.info("Connected to the server");
//     String sessionId = session.getId();
//     sessions.put(sessionId, session);

//     Message message = Message.builder()
//       .sender(sessionId)
//       .reciver("all")
//       .build();

//     message.newConnect();

//     sessions.values().forEach(s -> {
//       try {
//         if (!s.getId().equals(sessionId)) {
//           s.sendMessage(new TextMessage("hello this is " + sessionId));
//         }
//       } catch (Exception e) {
//         log.error(e.getMessage());
//       }
//     });
//     super.afterConnectionEstablished(session);
//   }

//   // @Override
//   // protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
//   //   Message message = mapper.readValue(textMessage.getPayload(), Message.class);
//   //   message.setSender(session.getId());

//   //   log.info("### {}", message.getData());
//   //   log.info("### {}", message.getReceiver());
//   //   WebSocketSession receiver = sessions.get(message.getReceiver());
//   //   if (receiver != null && receiver.isOpen() {
//   //     receiver.sendMessage(new TextMessage(message.writeValueAsString(message)));
//   //   }
//   // }
// }