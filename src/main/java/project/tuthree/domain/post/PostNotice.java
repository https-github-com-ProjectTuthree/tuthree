package project.tuthree.domain.post;

import lombok.Getter;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Admin;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@SequenceGenerator(
        name = "POSTNOTICE_SEQ_GENERATOR",
        sequenceName = "POSTNOTICE_SEQ",
        allocationSize = 1
)
public class PostNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTNOTICE_SEQ_GENERATOR")
    @Column(name = "post_id")
    private String id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private String type;

    private String title;

    private String content;

    private Long view;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "write_at")
    private Date writeAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "alter_at")
    private Date alterAt;

    private Status secret;
}
