package project.tuthree.service.push;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.tuthree.dto.room.ChatroomDTO;
import project.tuthree.mapper.ChatRoomMapper;
import project.tuthree.repository.ChatRepository;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomMapper chatRoomMapper;
    //아마 채팅 하나하나마다 bool 체크를 해서 안 읽은 것 확인하는듯

//    /** 채팅방 개설 */
//    public Long makeChatRoomByIds(String user1, String user2) {
//        //이미 채팅방이 있을 경우 리다이렉트 해줘야 함
//        Long chatRoom = chatRepository.findChatRoom(user1, user2);
//        if(chatRoom == -1) return chatRoom;
//        ChatroomDTO chatroomDTO = new ChatroomDTO(user1, user2);
//        return chatRepository.makeChatRoom(chatRoomMapper.toEntity(chatroomDTO));
//    }

    //채팅방 아이디로 로그 전체 찾기
    //채팅방 아이디 & 안 읽은 로그
    //안 읽은 로그 개수 반환 - 사람 아이디
    //읽은 것들 로그 변경
    //닉네임으로 채팅룸 전체 찾기
    //채팅룸 만들기
    //채팅 로그 추가


}
