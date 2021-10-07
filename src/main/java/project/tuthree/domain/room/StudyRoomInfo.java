package project.tuthree.domain.room;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Teacher;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@RequiredArgsConstructor
@IdClass(StudyRoomId.class)
@Table(name = "study_room_info")
public class StudyRoomInfo implements Serializable {

    /**
     * 복합키 식별 관계 매핑
     */

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"),
            @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    })
    private StudyRoom id;

    private String subject;

    @Column(name = "room_cost")
    private int cost;

    @Column(name = "room_day")
    private String day;

    @Column(name = "room_start")
    private String start;

    @Column(name = "room_end")
    private String end;

    private String detail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_date")
    private Date checkDate;

    private boolean status; //학생의 수락 상태
    /**
     * true : ok
     * false : no
     */

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Builder
    public StudyRoomInfo(StudyRoom id, String subject, int cost, String day, String start, String end, String detail, Date checkDate, boolean status) {
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

    public void infoUpdate(StudyRoomInfo studyRoomInfo) {
        this.subject = studyRoomInfo.getSubject();
        this.cost = studyRoomInfo.getCost();
        this.day = studyRoomInfo.getDay();
        this.start = studyRoomInfo.getStart();
        this.end = studyRoomInfo.getEnd();
        this.detail = studyRoomInfo.getDetail();
        this.status = studyRoomInfo.isStatus();
    }

    public void acceptInfo(){
        this.status = true;
    }
}
