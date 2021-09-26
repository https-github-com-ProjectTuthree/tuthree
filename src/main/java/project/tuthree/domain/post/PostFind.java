package project.tuthree.domain.post;

import lombok.Getter;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;
import project.tuthree.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@SequenceGenerator(
        name = "POSTFIND_SEQ_GENERATOR",
        sequenceName = "POSTFIND_SEQ",
        allocationSize = 1
)
public class PostFind implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTFIND_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    private Long view;

    @Temporal(TemporalType.TIMESTAMP)
    private Date write_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alter_at;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherId;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "student_id")
    private Student studentId;


}
