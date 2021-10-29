package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.Chat;
import project.tuthree.domain.room.ChatRoom;
import project.tuthree.dto.ChatDTO;
import project.tuthree.dto.room.ChatroomDTO;

@Mapper(componentModel = "spring")
public interface ChatMapper extends GenericMapper<ChatDTO, Chat> {
    ChatDTO toDto(Chat chat);

    Chat toEntity(ChatDTO chatDTO);

}
