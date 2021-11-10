package project.tuthree.dto.room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import project.tuthree.domain.room.StudyRoom;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarDTO {
    private Long id;

    private StudyRoom studyRoomId;

    @NotBlank(message = "스터디룸 일정 날짜 입력값 필요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateAt;

    @NotBlank(message = "스터디룸 일정 내용 입력값 필요")
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
