package project.tuthree.domain.room;

import lombok.Getter;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
//@DiscriminatorColumn(name = "study_room")
@Getter
@SequenceGenerator(
        name = "STUDY_SEQ_GENERATOR",
        sequenceName = "STUDY_SEQ",
        allocationSize = 1
)
public class StudyRoom{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "STUDY_SEQ_GENERATOR")
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Enumerated(EnumType.STRING)
    private Status room_status;
}
