package project.tuthree.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CHAT_SEQ_GENERATOR")
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom room;

    @Column(name = "user_id")
    private String userId; //sender, 1:1 채팅이라서 receiver의 아이디는 필요없다.

    @Column(name = "user_name")
    private String name; //다른 테이블을 사용하지 않고 바로 넘겨주기 위함

    @Temporal(TemporalType.DATE)
    @Column(name = "chat_at")
    private Date chatAt;

    @Column(name = "chat_content")
    private String content;

    @Column(name = "is_read")
    private boolean read; //채팅을 읽었는지 확인하기 위함

    @Builder
    public Chat(ChatRoom room, String userId, String name, Date chatAt, String content, boolean read) {
        this.room = room;
        this.userId = userId;
        this.name = name;
        this.chatAt = chatAt;
        this.content = content;
        this.read = read;
    }
}
