package project.tuthree.domain.post;

import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Admin;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "POSTNOTICE_SEQ_GENERATOR",
        sequenceName = "POSTNOTICE_SEQ",
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTNOTICE_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

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

    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @Builder
    public PostNotice(Admin admin, String title, String content, Long view, Date writeAt, Date alterAt, Status secret, NoticeType type) {
        this.admin = admin;
        this.title = title;
        this.content = content;
        this.view = view;
        this.writeAt = writeAt;
        this.alterAt = alterAt;
        this.secret = secret;
        this.type = type;
    }
}
