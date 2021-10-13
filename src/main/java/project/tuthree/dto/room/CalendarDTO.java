package project.tuthree.dto.room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarDTO {
    private Long id;

    private StudyRoom studyRoomId;

    private Date dateAt;

    private String schedule;

    @Builder
    public CalendarDTO(Long id, StudyRoom studyRoomId, Date dateAt, String schedule) {
        this.id = id;
        this.studyRoomId = studyRoomId;
        this.dateAt = dateAt;
        this.schedule = schedule;
    }

    public void updateDTO(StudyRoom studyRoom) {
        this.studyRoomId = studyRoom;
    }
}
