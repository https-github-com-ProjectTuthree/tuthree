package project.tuthree.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "chat")
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CHAT_SEQ_GENERATOR")
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom room;

    @Column(name = "user_id")
    private String userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "chat_at")
    private Date chatAt;

    @Column(name = "chat_content")
    private String content;

}
