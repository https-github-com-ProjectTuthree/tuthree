package project.tuthree.domain.room;

import lombok.Getter;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.FetchType.*;

@Entity
@Getter
//@IdClass(StudyRoomId.class)
public class StudyRoom implements Serializable {
    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherId;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student studentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status")
    private Status Status;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
