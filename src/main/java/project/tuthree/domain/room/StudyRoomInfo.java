package project.tuthree.domain.room;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Teacher;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Column(name = "room_cost")
    private String cost;

    @Lob
    private byte[] info; //subject, day, start, end

    private String detail;

    @Temporal(TemporalType.DATE)
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
    public StudyRoomInfo(StudyRoom id, String cost, byte[] info, String detail, Date checkDate, boolean status) {
        this.id = id;
        this.cost = cost;
        this.info = info;
        this.detail = detail;
        this.checkDate = checkDate;
        this.status = status;
    }

    public void acceptInfo(){
        this.status = true;
    }

    public void infoUpdate(StudyRoomInfo studyRoomInfo) {
        this.cost = studyRoomInfo.getCost();
        this.info = studyRoomInfo.getInfo();
        this.detail = studyRoomInfo.getDetail();
        this.status = studyRoomInfo.isStatus();
    }
}
