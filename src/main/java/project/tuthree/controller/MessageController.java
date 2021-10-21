package project.tuthree.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import project.tuthree.config.FireBaseConfig;
import project.tuthree.dto.ChatDTO;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final FireBaseConfig init;

//    @GetMapping("/v1")
    public String v1() throws IOException {

//        init.init();
        return "test1";
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ChatDTO sendMessage(final ChatDTO chatDTO) throws InterruptedException {
        Thread.sleep(1000);

        log.info("chatdto message : ");
        return new ChatDTO(chatDTO.getRoom(), chatDTO.getUserId(), chatDTO.getContent());
    }

}
