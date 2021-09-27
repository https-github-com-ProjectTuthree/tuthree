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
public class PostTestPaper {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTTESTPAPER_SEQ_GENERATOR")
    @Column(name = "post_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacherId;

    private String title;
    private String content;

    private Long view;

    @Temporal(TemporalType.TIMESTAMP)
    private Date write_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alter_at;

    private Status secret;

}
