package project.tuthree.domain;


import lombok.Getter;
import project.tuthree.domain.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "plus_id")
    private String user2;
}
