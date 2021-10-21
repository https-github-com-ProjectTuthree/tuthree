package project.tuthree.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.ChatRoom;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatDTO {

    private Long id;
    private ChatRoom room;
    private String userId;
    private Date chatAt;
    private String content;

    @Builder
    public ChatDTO(Long id, ChatRoom room, String userId, Date chatAt, String content) {
        this.id = id;
        this.room = room;
        this.userId = userId;
        this.chatAt = chatAt;
        this.content = content;
    }

    public ChatDTO(ChatRoom room, String userId, String content) {
        this.room = room;
        this.userId = userId;
        this.content = content;
    }

    public ChatDTO(ChatDTO chatDTO) {
        this.room = chatDTO.getRoom();
        this.userId = chatDTO.getUserId();
        this.content = chatDTO.getContent();
    }
}
