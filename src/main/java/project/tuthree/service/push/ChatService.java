//package project.tuthree.service.push;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import project.tuthree.dto.ChatDTO;
//import project.tuthree.dto.room.ChatroomDTO;
//import project.tuthree.mapper.ChatMapper;
//import project.tuthree.mapper.ChatRoomMapper;
//import project.tuthree.repository.ChatRepository;
//
//import java.text.ParseException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ChatService {
//
//    private final ChatRepository chatRepository;
//    private final ChatRoomMapper chatRoomMapper;
//    private final ChatMapper chatMapper;
//
//    /** 채팅 전송 - 토픽 체인지, 채팅 로그 남기기 */
//    public Long sendChat(Long roomId, ChatDTO chatDTO) {
//        //queue 로 보내는 로직을 짠다. 리스터에 들어가면 거기서 sendbytoken, savechatlog
//        chatRepository.saveChatLog(chatMapper.toEntity(chatDTO));
//        return chatDTO.getId();
//    }
//
//    /** 채팅방 만들기 */
//    public Long addChatRoomByIds(String user1, String user2) {
//        Long id = chatRepository.findChatRoomByIds(user1, user2);
//        return (id != -1L) ? id :
//                chatRepository.makeChatRoom(chatRoomMapper.toEntity(new ChatroomDTO(user1, user2)));
//    }
//
//    /** 이전 채팅 목록 불러오기 */
//    public List<ChatDTO> findChatListByRoomId(Long id) {
//        return chatRepository.findChatListByRoomId(id).stream()
//                .map(m -> chatMapper.toDto(m)).collect(Collectors.toList());
//    }
//
//    /** 전체 채팅방 + 마지막 채팅 + 읽지 않은 채팅 수 */
//    public List<chatListDTO> findChatNotRead(String id) throws ParseException {
//        List<chatListDTO> chatRoomById = chatRepository.findChatRoomById(id);
//        chatRoomById.stream().forEach(m -> m.updateNotRead(m.roomId));
//        return chatRoomById;
//    }
//    //아마 채팅 하나하나마다 bool 체크를 해서 안 읽은 것 확인하는듯
//
//    //채팅방 아이디로 로그 전체 찾기
//    //채팅방 아이디 & 안 읽은 로그
//    //안 읽은 로그 개수 반환 - 사람 아이디
//    //읽은 것들 로그 변경
//    //닉네임으로 채팅룸 전체 찾기
//    //채팅룸 만들기
//    //채팅 로그 추가
//
//    @Getter
//    public static class chatListDTO {
//        Long roomId;
//        String user1;
//        String user2;
//        String lastChat;
//        String lastDate;
//        Long notRead;
//
//        public chatListDTO(Long roomId, String user1, String user2, String lastChat, String lastDate) {
//            this.roomId = roomId;
//            this.user1 = user1;
//            this.user2 = user2;
//            this.lastChat = lastChat;
//            this.lastDate = lastDate;
//        }
//
//        public void updateNotRead(Long count) {
//            this.notRead = count;
//        }
//    }
//
//
//}
