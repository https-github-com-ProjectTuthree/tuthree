package project.tuthree.domain.room;

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

    private int cost;

    private String day;

    private String start;

    private String end;

    private String detail;

    @Temporal(TemporalType.TIMESTAMP)
    private Date check_date;

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
}
