package project.tuthree.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import project.tuthree.domain.user.Grade;
import project.tuthree.dto.ChatDTO;
import project.tuthree.service.NoticeService;
import project.tuthree.service.NoticeService.keywordPushDTO;
import project.tuthree.service.push.ChatService;
import project.tuthree.service.push.PushService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FcmTestApiController {

    private final SimpMessageSendingOperations messageSendingOperations;
    private final FcmTestController controller;
    private final NoticeService noticeService;

    @GetMapping("/testFcm")
    public void test_fcm() {
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class exDTO {
        String sender;
        String receiver;
        String name;
        String grade;
    }

    @PostMapping("/keyword-msg")
    public boolean sendKeyword(@RequestBody exDTO dto) {
        if(dto.grade.equals("parent")){
            noticeService.ParentChildRegisterNotice(new keywordPushDTO(dto.getSender(), dto.getReceiver(), dto.getName(), Grade.PARENT.getStrType()));

        } else if (dto.grade.equals("student")) {
            noticeService.ParentChildRegisterNotice(new keywordPushDTO(dto.getSender(), dto.getReceiver(), dto.getName(), Grade.STUDENT.getStrType()));
        }
        return true;
    }
}
