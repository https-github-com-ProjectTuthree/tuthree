package project.tuthree.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import project.tuthree.domain.room.StudyRoom;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PoststudyDTO {
    private Long id;

    private StudyRoom studyRoomId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "수업 보고서 날짜 입력값 필요")
    private Date date;

    @NotBlank(message = "수업 보고서 차수 입력값 필요")
    private Long number;

    private String start;
    private String end;
    @NotBlank(message = "수업 보고서 내용 입력값 필요")
    private String detail;

    @Builder
    public PoststudyDTO(Long id, StudyRoom studyRoomId, Date date, Long number, String start, String end, String detail) {
        this.id = id;
        this.studyRoomId = studyRoomId;
        this.date = date;
        this.number = number;
        this.start = start;
        this.end = end;
        this.detail = detail;
    }

    public void updateDTO(StudyRoom studyRoom) {
        this.studyRoomId = studyRoom;
    }

}
