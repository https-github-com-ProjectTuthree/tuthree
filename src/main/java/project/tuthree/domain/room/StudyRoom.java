package project.tuthree.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.FetchType.*;

@Entity
@Getter
//@IdClass(StudyRoomId.class)
@Table(name = "study_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRoom implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacherId;

    @Id
    @ManyToOne
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

    @Builder
    public StudyRoom(Teacher teacherId, Student studentId, project.tuthree.domain.Status status) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        Status = status;
    }

    public void openStudyRoom() {
        this.Status = Status.OPEN;
    }

    public void closeStudyRoom() {
        this.Status = Status.CLOSE;
    }
}
