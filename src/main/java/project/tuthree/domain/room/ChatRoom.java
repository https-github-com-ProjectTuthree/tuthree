package project.tuthree.domain.room;

import lombok.Getter;
import project.tuthree.domain.user.Admin;

import javax.persistence.*;

@Entity
//@DiscriminatorColumn(name = "chat_room")
@Getter
public class ChatRoom {
    @Id
    @Column(name = "room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Admin user1;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Admin user2;
}
