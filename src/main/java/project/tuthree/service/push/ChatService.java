package project.tuthree.service.push;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import project.tuthree.domain.Chat;
import project.tuthree.domain.Status;
import project.tuthree.domain.room.ChatRoom;
import project.tuthree.dto.ChatDTO;
import project.tuthree.dto.room.ChatroomDTO;
import project.tuthree.mapper.ChatMapper;
import project.tuthree.mapper.ChatRoomMapper;
import project.tuthree.repository.ChatRepository;
import project.tuthree.repository.UserFileRepository;
import project.tuthree.service.push.ChatService.chatRoomDTO.chatListDTO;

import static project.tuthree.exception.ExceptionSupplierImpl.wrap;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessageSendingOperations msgOperations;
    private final RabbitService rabbitService;
    private final ChatRepository chatRepository;
    private final ChatRoomMapper chatRoomMapper;
    private final UserFileRepository userFileRepository;
    private final ChatMapper chatMapper;

    @Getter
    @AllArgsConstructor
    class sendChatDTO {
        String name;
        String content;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class chatRoomNameDTO {
        private String senderId;
        private String senderName;
        private String receiverId;
    }

    /** 채팅 전송 - 토픽 체인지, 채팅 로그 남기기 */
    public Long sendChat(ChatDTO chatDTO) {
        msgOperations.convertAndSend("/topic/messages." + String.valueOf(chatDTO.getRoom().getId()), new sendChatDTO(chatDTO.getName(), chatDTO.getContent()));
        //chatdto update
        ChatRoom room = chatRepository.findChatRoomByRoomId(chatDTO.getRoom().getId());
        chatDTO.update(room);
        chatRepository.saveChatLog(chatMapper.toEntity(chatDTO));
        //토픽 전송하기
        rabbitService.rabbitChatProducer(chatDTO);
        return chatDTO.getId();
    }

    /** 채팅방 만들기 */
    public Long addChatRoomByIds(chatRoomNameDTO dto) {

        String sender = dto.getSenderId();
        String receiver = dto.getReceiverId();
        Long id = chatRepository.findChatRoomByIds(sender, receiver);
        //채팅방이 없을 경우 만들기
        if (id == -1L) {
            ChatRoom chatRoom = chatRepository.makeChatRoom(chatRoomMapper.toEntity(new ChatroomDTO(sender, receiver)));

            chatRepository.saveChatLog(chatMapper.toEntity(new ChatDTO(chatRoom, sender, dto.getSenderName(), "채팅이 시작되었습니다")));
            return chatRoom.getId();
        }
        return id;
    }

    /** 이전 채팅 목록 불러오기 */
    public chatRoomDTO findChatListByRoomId(Long id, String userId) {
        List<chatListDTO> list = new ArrayList<>();
        List<Chat> chatListByRoomId = chatRepository.findChatListByRoomId(id, userId);
        chatListByRoomId.stream()
                        .forEach(m -> wrap(()->list.add( new chatListDTO(m.getUserId(), m.getName(), m.getContent(), userFileRepository.unixToTimestamp(m.getChatAt()), m.isRead()))));
        return new chatRoomDTO(id, list);
    }

    /** 전체 채팅방 + 마지막 채팅 + 읽지 않은 채팅 수 */
    public List<chatRoomListDTO> findChatNotRead(String userId) throws ParseException {
        List<chatRoomListDTO> chatRoomById = chatRepository.findChatRoomWLogById(userId);
        Map<Long, Long> notRead = chatRepository.findChatNotReadByUserId(userId);
        chatRoomById.stream().forEach(m -> m.chatList.updateNotRead(
                (notRead.get(m.roomId) == null) ? 0L : notRead.get(m.roomId)));
        return chatRoomById;
    }
    //아마 채팅 하나하나마다 bool 체크를 해서 안 읽은 것 확인하는듯

    //채팅방 아이디로 로그 전체 찾기
    //채팅방 아이디 & 안 읽은 로그
    //안 읽은 로그 개수 반환 - 사람 아이디
    //읽은 것들 로그 변경
    //닉네임으로 채팅룸 전체 찾기
    //채팅룸 만들기
    //채팅 로그 추가

    /** 채팅방 하나 채팅 목록 */
    @Getter
    @AllArgsConstructor
    public static class chatRoomDTO {
        Long roomId;
        List<chatListDTO> chatList;

        @Getter
        @AllArgsConstructor
        public static class chatListDTO {
            String senderId;
            String senderName;
            String chat;
            String date;
            boolean read;
        }
    }

    /** 개인의 전체 채팅 목록 */
    @Getter
    @AllArgsConstructor
    public static class chatRoomListDTO {
        Long roomId;
        chatListDTO chatList;

        @Getter
        public static class chatListDTO {
            String senderId;
            String name;
            Status registration;
            String chat;
            String date;
            Long notRead;

            public chatListDTO(String senderId, String name, Status registration, String chat, String date) {
                this.senderId = senderId;
                this.name = name;
                this.registration = registration;
                this.chat = chat;
                this.date = date;
                this.notRead = 0L;
            }

            public void updateNotRead(Long notRead) {
                this.notRead = notRead;
            }
        }
    }

}
