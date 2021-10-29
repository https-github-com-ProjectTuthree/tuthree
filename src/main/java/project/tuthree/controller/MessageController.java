package project.tuthree.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import project.tuthree.domain.room.ChatRoom;
import project.tuthree.dto.ChatDTO;
import project.tuthree.repository.ChatRepository;
import project.tuthree.service.push.RabbitService;

import java.net.URLDecoder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final RabbitService rabbitService;
    private final ChatRepository chatRepository;
//
//    @MessageMapping("/message")
//    @SendTo("/topic/messages.2")
//    public MessageDTO sendMessage(final ChatDTO chatDTO) throws InterruptedException {
//        Thread.sleep(1000);
//        ChatRoom chatRoomByRoomId = chatRepository.findChatRoomByRoomId(chatDTO.getRoom().getId());
//        chatDTO.update(chatRoomByRoomId, chatDTO.getUserId(), URLDecoder.decode(chatDTO.getName()), URLDecoder.decode(chatDTO.getContent()));
//        rabbitService.rabbitChatProducer(chatDTO);
//        return new MessageDTO(chatDTO.getContent());
//    }
}
