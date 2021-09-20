package project.tuthree.domain.post;

import lombok.Getter;
import project.tuthree.domain.room.StudyRoom;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
public class PostReview implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "room_id")
    private StudyRoom id;

    private int star;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime write_at;
}
