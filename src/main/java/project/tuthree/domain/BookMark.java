package project.tuthree.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@SequenceGenerator(
        name = "BOOKMARK_SEQ_GENERATOR",
        sequenceName = "BOOKMARK_SEQ",
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookMark {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "BOOKMARK_SEQ_GENERATOR")
    @Column(name = "mark_id")
    private Long id;

    @Column(name = "user_id1")
    private String user1;

    @Column(name = "user_id2")
    private String user2;

    @Builder
    public BookMark(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
