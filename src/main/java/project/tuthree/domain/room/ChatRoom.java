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
    //동일한 연과노간계 두개 가 ㅐ핑이 되지 않음
    private Admin user1;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private Admin user2;
}
