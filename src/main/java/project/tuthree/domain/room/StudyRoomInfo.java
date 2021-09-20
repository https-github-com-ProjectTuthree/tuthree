package project.tuthree.domain.room;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.tuthree.domain.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class StudyRoomInfo implements Serializable {
    @Id @OneToOne
    @JoinColumn(name = "room_id")
    private StudyRoom id;

    private String subject;
    private int cost;
    private String detail;

    @Column(name = "draw_date")
    private String date;
    private String bank;
    private String account;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime check_date;

    @Enumerated(EnumType.STRING)
    private Status status; //학생의 수락 상태

}
