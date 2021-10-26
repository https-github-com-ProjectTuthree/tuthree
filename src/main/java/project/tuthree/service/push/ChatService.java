package project.tuthree.service.push;

import com.querydsl.core.Tuple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /** 채팅 전송 - 토픽 체인지, 채팅 로그 남기기 */
    public Long sendChat(ChatDTO chatDTO) {
        msgOperations.convertAndSend("/topic/messages." + String.valueOf(chatDTO.getRoom().getId()), new sendChatDTO(chatDTO.getName(), chatDTO.getContent()));
        //chatdto update
        ChatRoom room = chatRepository.findChatRoomByRoomId(chatDTO.getRoom().getId());
        chatDTO.update(room);
        //토픽 전송하기
        rabbitService.rabbitChatProducer(chatDTO);
        return chatDTO.getId();
    }

    /** 채팅방 만들기 */
    public Long addChatRoomByIds(String sender, String receiver) {
        Long id = chatRepository.findChatRoomByIds(sender, receiver);
        //채팅방이 없을 경우 만들기
        return (id != -1) ? id :
            chatRepository.makeChatRoom(chatRoomMapper.toEntity(new ChatroomDTO(sender, receiver)));
    }

    /** 이전 채팅 목록 불러오기 */
    public List<chatRoomDTO> findChatListByRoomId(Long id) {
        return chatRepository.findChatListByRoomId(id).stream()
                .map(m -> wrap(() -> new chatRoomDTO(m.getRoom().getId(), new chatListDTO(m.getUserId(), m.getName(), m.getContent(), userFileRepository.unixToTimestamp(m.getChatAt()), m.isRead()))))
                .collect(Collectors.toList());
    }

    /** 전체 채팅방 + 마지막 채팅 + 읽지 않은 채팅 수 */
    public List<chatRoomListDTO> findChatNotRead(String userId) throws ParseException {
        List<ChatRoom> chatRooms = chatRepository.findChatRoomById(userId);
        List<chatRoomListDTO> chatRoomById = chatRepository.findChatRoomWLogById(userId);
        Map<Long, Long> notRead = chatRepository.findChatNotReadByUserId(userId);
        chatRoomById.stream().forEach(m -> m.chatList.updateNotRead(
                (notRead.get(m.roomId)==null)?0L:notRead.get(m.roomId)));
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

    @Getter
    @AllArgsConstructor
    public static class chatRoomDTO {
        Long roomId;
        chatListDTO chatList;

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

    @Getter
    @AllArgsConstructor
    public static class chatRoomListDTO {
        Long roomId;
        chatListDTO chatList;

        @Getter
        public static class chatListDTO {
            String senderId;
            String senderName;
            String chat;
            String date;
            Long notRead;

            public chatListDTO(String senderId, String senderName, String chat, String date) {
                this.senderId = senderId;
                this.senderName = senderName;
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
