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
@Table(name = "post_find")
public class PostFind implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTFIND_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherId;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "student_id")
    private Student studentId;

    @Column(name = "post_view")
    private Long view;

    @Column(name = "write_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date writeAt;

    @Column(name = "alter_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alterAt;

}
