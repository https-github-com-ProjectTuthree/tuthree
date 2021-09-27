package project.tuthree.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@SequenceGenerator(
        name = "CALENDAR_SEQ_GENERATOR",
        sequenceName = "CALENDAR_SEQ",
        allocationSize = 1
)
public class Calendar {
    /**
     * 복합키 식별 관계 매핑... 하나도 모르겠다..
     * 결국 복합키 안하는 걸로
     */

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CALENDAR_SEQ_GENERATOR")
    @Column(name = "cal_id")
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"),
            @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    })
    private StudyRoom studyRoomId;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String schedule;


}
