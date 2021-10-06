package project.tuthree.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class PostfindDTO {

    private Long id;

    private Teacher teacherId;

    private Student studentId;

    private Long view;

    private Date writeAt;

    private Date alterAt;

    @Builder
    public PostfindDTO(Long id, Teacher teacherId, Student studentId, Long view, Date writeAt, Date alterAt) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.view = view;
        this.writeAt = writeAt;
        this.alterAt = alterAt;
    }
}
