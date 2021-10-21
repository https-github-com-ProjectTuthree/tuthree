package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.room.ChatRoom;
import project.tuthree.dto.room.ChatroomDTO;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper extends GenericMapper<ChatroomDTO, ChatRoom> {
    ChatroomDTO toDto(ChatRoom chatRoom);

    ChatRoom toEntity(ChatroomDTO chatroomDTO);
}
