package project.tuthree.domain;


import lombok.Getter;
import project.tuthree.domain.user.User;

import javax.persistence.*;


@Entity
@Getter
@SequenceGenerator(
        name = "BOOKMARK_SEQ_GENERATOR",
        sequenceName = "BOOKMARK_SEQ",
        allocationSize = 1
)
public class BookMark {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "BOOKMARK_SEQ_GENERATOR")
    @Column(name = "mark_id")
    private Long id;

    @Column(name = "user_id1")
    private String user;

    @Column(name = "user_id2")
    private String user2;
}
