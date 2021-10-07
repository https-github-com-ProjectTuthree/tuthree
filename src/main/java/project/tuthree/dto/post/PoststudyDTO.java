package project.tuthree.dto.post;

import lombok.Builder;
import lombok.Getter;
import project.tuthree.domain.room.StudyRoom;

import java.util.Date;

@Getter
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
}
