package project.tuthree.domain.post;

import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Admin;

import javax.persistence.*;

import java.util.Date;


@Entity
@Getter
@SequenceGenerator(
        name = "POSTFAQ_SEQ_GENERATOR",
        sequenceName = "POSTFAQ_SEQ",
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_faq")
public class PostFaq {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTFAQ_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "post_type")
    @Enumerated(EnumType.STRING)
    private FaqType type;

    private String title;

    @Column(name = "post_content")
    private String content;

    @Column(name = "post_view")
    private Long view;

    @Temporal(TemporalType.DATE)
    @Column(name = "write_at")
    private Date writeAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "alter_at")
    private Date alterAt;

    @Column(name = "post_secret")
    private Status secret;

    @Builder
    public PostFaq(Admin admin, String title, String content, Long view, Date writeAt, Date alterAt, Status secret, FaqType type) {
        this.admin = admin;
        this.title = title;
        this.content = content;
        this.view = view;
        this.writeAt = writeAt;
        this.alterAt = alterAt;
        this.secret = secret;
        this.type = type;
    }

    public void updateView() {
        this.view += 1;
    }

    public void updateFaq(PostFaq postFaq) {
        this.title = postFaq.getTitle();
        this.content = postFaq.getContent();
        this.secret = postFaq.getSecret();
        this.type = postFaq.getType();
    }

}
