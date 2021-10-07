package project.tuthree.dto.room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.StudyRoom;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyroomInfoDTO {

    /**
     * 복합키 식별 관계 매핑
     */
    private StudyRoom id;
    private String subject;
    private int cost;
    private String day;
    private String start;
    private String end;
    private String detail;
    private Date checkDate;
    private boolean status; //학생의 수락 상태

    @Builder
    public StudyroomInfoDTO(StudyRoom id, String subject, int cost, String day, String start, String end, String detail, Date checkDate, boolean status) {
        this.id = id;
        this.subject = subject;
        this.cost = cost;
        this.day = day;
        this.start = start;
        this.end = end;
        this.detail = detail;
        this.checkDate = checkDate;
        this.status = status;
    }

    public void updateId(StudyRoom studyRoom) {
        this.id = studyRoom;
        this.checkDate = new Date();
        this.status = false;
    }
}
