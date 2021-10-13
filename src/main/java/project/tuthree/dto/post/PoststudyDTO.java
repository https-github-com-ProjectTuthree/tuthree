package project.tuthree.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.StudyRoom;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PoststudyDTO {
    private Long id;

    private StudyRoom studyRoomId;

    private Date date;
    private Long number;

    private String start;
    private String end;

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
