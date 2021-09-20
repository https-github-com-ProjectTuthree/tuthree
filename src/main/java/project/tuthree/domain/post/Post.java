package project.tuthree.domain.post;

import lombok.Getter;
import project.tuthree.domain.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@SequenceGenerator(
        name = "post_seq_generator",
        sequenceName = "oracle_sequence_object_name",
        initialValue = 1,
        allocationSize = 1 /** 이게 뭐지...흠...*/
)
public abstract class Post {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "board_id")
    private Long boardId;

    private String title;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "wirte_at")
    private Date writeAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "alter_at")
    private Date alterAt;

    @Enumerated(EnumType.STRING)
    private Status secret;
}
