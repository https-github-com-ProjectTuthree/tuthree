package project.tuthree.domain;

import lombok.Getter;
import project.tuthree.domain.room.ChatRoom;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@SequenceGenerator(
        name = "CHAT_SEQ_GENERATOR",
        sequenceName = "CHAT_SEQ",
        allocationSize = 1
)
public class Chat {

    //chat admin 은 고려하지 않음
    //어떻게 해야되냐..

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHAT_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom room;

    @Column(name = "user_id")
    private String userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "chat_at")
    private Date chatAt;

    @Column(name = "chat_content")
    private String chatContent;


}
