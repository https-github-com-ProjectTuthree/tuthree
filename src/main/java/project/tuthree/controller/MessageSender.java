//package project.tuthree.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class MessageSender {
//
//    private final RabbitMessagingTemplate template;
//
//    public void send(Long id, String message) {
//        log.info("chatsender.sender : " + message);
////        template.convertAndSend("chat-exchange", "/room/test", message);
////        template.convertAndSend("/topic/messages", message);
////        template.convertAndSend("/topic/messages", message);
//        template.convertAndSend("chat-exchange", "room." + String.valueOf(id) ,message);
//    }
//}
