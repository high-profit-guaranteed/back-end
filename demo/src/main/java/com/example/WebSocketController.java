package com.example;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Controller
public class WebSocketController {
  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  Greeting greeting(HelloMessage message) {
    //service 호출
    return new Greeting("Hello, ${HtmlUtils.htmlEscape(message.name)} !");
  }
}

@Data
class HelloMessage {
  private String name;
}

@Data
@AllArgsConstructor
class Greeting {
  private String content;
}