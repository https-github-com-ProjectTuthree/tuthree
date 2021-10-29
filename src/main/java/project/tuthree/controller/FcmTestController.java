package project.tuthree.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FcmTestController {

    @GetMapping("/fcmTest")
    public String fcm_test() {
        log.info("fcm_test===============");
        return "test1";
    }

    @GetMapping("/room1")
    public String room1() {

        log.info("room1 test ============");
        return "chatRoom1";
    }

    @GetMapping("/room2")
    public String room2() {

        log.info("room2 test ============");
        return "chatRoom2";
    }

    @GetMapping("/test-room")
    public String test_room() {

        log.info("room 10000 test ============");
        return "room";
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MessageDTO {
        private String content;
    }

}
