package project.tuthree.domain.room;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@SequenceGenerator(
        name = "CALENDAR_SEQ_GENERATOR",
        sequenceName = "CALENDAR_SEQ",
        allocationSize = 1
)
public class Calendar {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CALENDAR_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private StudyRoom room;

    @Temporal(TemporalType.DATE)
    private LocalDateTime date;

    private String schedule;
    private String detail;
}
