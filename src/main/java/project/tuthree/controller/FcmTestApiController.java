package project.tuthree.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import project.tuthree.dto.ChatDTO;
import project.tuthree.service.push.ChatService;
import project.tuthree.service.push.PushService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FcmTestApiController {

    private final SimpMessageSendingOperations messageSendingOperations;
    private final FcmTestController controller;

    @GetMapping("/testFcm")
    public void test_fcm() {
        log.info("============testFcm");
        controller.fcm_test();
//        return "fcmTest";
    }

    @PostMapping("/send-message")
    public boolean sendMessage(@RequestBody ChatDTO chatDTO) {
        messageSendingOperations.convertAndSend("/topic/messages/2", chatDTO);
//        pushService.sendByToken(chatDTO);
//        chatService.sendChat(chatDTO);
        return true;
    }
}
