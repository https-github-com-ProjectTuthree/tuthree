package project.tuthree.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Teacher;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "POSTTESTPAPER_SEQ_GENERATOR",
        sequenceName = "POSTTESTPAPER_SEQ",
        allocationSize = 1
)
@Table(name = "post_testpaper")
public class PostTestPaper {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTTESTPAPER_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Teacher userId;

    private String title;

    @Column(name = "post_content")
    private String content;

    @Column(name = "post_view")
    private Long view;

    @Column(name = "write_at")
    @Temporal(TemporalType.DATE)
    private Date writeAt;

    @Column(name = "alter_at")
    @Temporal(TemporalType.DATE)
    private Date alterAt;

    @Column(name = "post_secret")
    private Status secret;

    public void updateView() {
        this.view += 1;
    }

    @Builder
    public PostTestPaper(Teacher userId, String title, String content, Long view, Date writeAt, Date alterAt, Status secret) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.view = view;
        this.writeAt = writeAt;
        this.alterAt = alterAt;
        this.secret = secret;
    }

    public void updateTestPaper(PostTestPaper post) {
        this.userId = post.getUserId();
        this.content = post.getContent();
        this.alterAt = post.getAlterAt();
        this.secret = post.getSecret();
    }
}
