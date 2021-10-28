package project.tuthree.domain.room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.user.Admin;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(
        name = "CHATROOM_SEQ_GENERATOR",
        sequenceName = "CHATROOM_SEQ",
        allocationSize = 1
)
@Table(name = "chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CHATROOM_SEQ_GENERATOR")
    @Column(name = "room_id")
    private Long id;

    @Column(name = "user_id1")
    private String user1;

    @Column(name = "user_id2")
    private String user2;

    @Builder
    public ChatRoom(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
