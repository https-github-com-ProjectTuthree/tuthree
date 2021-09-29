package project.tuthree.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Teacher userId;

    private String title;

    @Column(name = "post_content")
    private String content;

    @Column(name = "post_view")
    private Long view;

    @Column(name = "write_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date writeAt;

    @Column(name = "alter_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alterAt;

    @Column(name = "post_secret")
    private Status secret;

}
