package project.tuthree.domain.room;

import lombok.Getter;
import project.tuthree.domain.user.Admin;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(
        name = "CHATROOM_SEQ_GENERATOR",
        sequenceName = "CHATROOM_SEQ",
        allocationSize = 1
)
public class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CHATROOM_SEQ_GENERATOR")
    @Column(name = "room_id")
    private Long id;

    @Column(name = "user_id1")
    private String user1;

    @Column(name = "user_id2")
    private String user2;

}
