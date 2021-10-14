package project.tuthree.dto.room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.StudyRoom;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyroomInfoDTO {

    private StudyRoom id;

    private String cost;

    private byte[] info; //subject, day, start, end
    /**
     * List : subject
     * Hashmap : day <hashmap1>
     * Hashmap : <hashmap1> start, end
     */

    /** entity로 변환되지 않는 정보 */
    private List<String> subject;

    private Map<String, Map<String, String>> schedule;

    private String detail;

    private Date checkDate;

    private boolean status; //학생의 수락 상태

    @Builder //entity로 변환되는 부분
    public StudyroomInfoDTO(StudyRoom id, String cost, byte[] info, String detail, Date checkDate, boolean status) {
        this.id = id;
        this.cost = cost;
        this.info = info;
        this.detail = detail;
        this.checkDate = checkDate;
        this.status = status;
    }

    //정보 입력 받는 부분 나중에 byte 변환해야함
    public StudyroomInfoDTO(StudyRoom id, String cost, List<String> subject, Map<String, Map<String, String>> schedule, String detail, Date checkDate, boolean status) {
        this.id = id;
        this.cost = cost;
        this.subject = subject;
        this.schedule = schedule;
        this.detail = detail;
        this.checkDate = checkDate;
        this.status = status;
    }


    public void insertBlob(byte[] blob) {
        this.info = blob;
    }

    public void updateId(StudyRoom studyRoom) {
        this.id = studyRoom;
        this.checkDate = new Date();
        this.status = false;
    }
}
