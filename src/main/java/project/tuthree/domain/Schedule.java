package project.tuthree.domain;


import lombok.Getter;
import project.tuthree.domain.room.StudyRoom;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "calendar")
@SequenceGenerator(
        name = "FILE_SEQ_GENERATOR",
        sequenceName = "FILE_SEQ",
        allocationSize = 1
)
public class Schedule {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "FILE_SEQ_GENERATOR")
    @Column(name = "cal_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private StudyRoom room;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String schedule;

    private String detail;
}
