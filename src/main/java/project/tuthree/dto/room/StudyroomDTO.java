package project.tuthree.dto.room;

import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyroomDTO {

    /** 각각이 외래키인 복합키 */
    private Teacher teacherId;

    private Student studentId;

    private Status Status;

    @Builder
    public StudyroomDTO(Teacher teacherId, Student studentId, Status status) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.Status = status;
    }
}
