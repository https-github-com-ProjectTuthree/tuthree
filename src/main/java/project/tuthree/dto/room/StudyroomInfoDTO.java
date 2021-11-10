package project.tuthree.dto.room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import project.tuthree.domain.room.StudyRoom;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyroomInfoDTO {

    private StudyRoom id;

    @NotBlank(message = "수업 계획서 급여 입력값 필요")
    private String cost;

    private byte[] info; //subject, day, start, end
    /**
     * List : subject
     * Hashmap : day <hashmap1>
     * Hashmap : <hashmap1> start, end
     */

    /** entity로 변환되지 않는 정보 */
    @NotNull(message = "수업 계획서 과외 과목 입력값 필요")
    private List<String> subject;

    @NotNull(message = "수업 계획서 수업 날짜 및 시간 입력값 필요")
    private Map<String, List<Map<String, String>>> schedule;

    private String detail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    @NotNull(message = "수업 계획서 학생 수락 여부 입력값 필요")
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
    public StudyroomInfoDTO(StudyRoom id, String cost, List<String> subject, Map<String, List<Map<String, String>>> schedule, String detail, Date checkDate, boolean status) {
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
