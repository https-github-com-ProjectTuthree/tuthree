package project.tuthree.domain.post;

import lombok.*;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_notice")
public class PostNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTNOTICE_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "post_type")
    @Enumerated(EnumType.STRING)
    private NoticeType type;

    private String title;

    @Column(name = "post_content")
    private String content;

    @Column(name = "post_view")
    private Long view;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "write_at")
    private Date writeAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "alter_at")
    private Date alterAt;

    @Column(name = "post_secret")
    private Status secret;


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

    public void updateNotice(PostNotice postNotice) {
        this.admin = postNotice.getAdmin();
        this.title = postNotice.getTitle();
        this.content = postNotice.getContent();
        this.alterAt = new Date();
        this.secret = postNotice.getSecret();
        this.type = postNotice.getType();
    }

    public void updateView() {
        this.view += 1;
    }
}
