package project.tuthree.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.tuthree.domain.room.ChatRoom;
import project.tuthree.dto.ChatDTO;
import project.tuthree.repository.ChatRepository;
import project.tuthree.service.push.ChatService;
import project.tuthree.service.push.ChatService.chatRoomNameDTO;
import project.tuthree.service.push.RabbitService;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class NoticeService {

    private final RabbitService rabbitService;
    private final ChatService chatService;
    private final ChatRepository chatRepository;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class keywordPushDTO {
        String sender;
        String receiver;
        String name;
        String grade;
    }

    /** 자녀 등록 알림 */
    public void ParentChildRegisterNotice(keywordPushDTO dto) {
        rabbitService.rabbitParentChildKeywordProducer(dto);
    }

    /** 회원 가입 채팅과 알림 (관리자의 채팅) */
    public void SignUpAdminNotice(String receiver) {
        chatRoomNameDTO chatDTO = new chatRoomNameDTO("tuthree10", "관리자", receiver);
        Long roomId = chatService.addChatRoomByIds(chatDTO);
        ChatRoom chatroom = chatRepository.findChatRoomByRoomId(roomId);
        ChatDTO chatDTO1 = new ChatDTO(chatroom, "tuthree10", "관리자", "TuThree 과외 플랫폼 회원가입을 축하드립니다");
        chatService.sendChat(chatDTO1);
        rabbitService.rabbitChatProducer(chatDTO1);
    }
//
//    public void PostAdminNotice(Long id) {
//
//    }

}
