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
    private String name;
    private Date chatAt;
    private String content;
    private boolean read;

    @Builder
    public ChatDTO(Long id, ChatRoom room, String userId, String name, Date chatAt, String content, boolean read) {
        this.id = id;
        this.room = room;
        this.userId = userId;
        this.name = name;
        this.chatAt = chatAt;
        this.content = content;
        this.read = read;
    }

    public ChatDTO(ChatRoom chatRoom, String userId, String name, String content) {
        this.room = chatRoom;
        this.userId = userId;
        this.name = name;
        this.chatAt = new Date();
        this.content = content;
        this.read = false;
    }

    public void update(ChatRoom chatRoom) {
        this.room = chatRoom;
        this.chatAt = new Date();
        this.read = false;
    }

}
