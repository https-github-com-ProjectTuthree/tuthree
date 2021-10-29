package project.tuthree.dto.room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatroomDTO {

    private Long id;
    private String user1;
    private String user2;

    @Builder
    public ChatroomDTO(Long id, String user1, String user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }

    public ChatroomDTO(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
