package project.tuthree.domain.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.StudyRoom;

import javax.persistence.*;

import java.util.Date;

@Entity
@Getter
@SequenceGenerator(
        name = "POSTSTUDY_SEQ_GENERATOR",
        sequenceName = "POSTSTUDY_SEQ",
        allocationSize = 1
)
@Table(name = "post_study")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTSTUDY_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"),
            @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    })
    private StudyRoom studyRoomId;

    @Column(name = "post_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "post_number")
    private Long number;

    @Column(name = "post_start")
    private String start;

    @Column(name = "post_end")
    private String end;

    private String detail;

    @Builder
    public PostStudy(StudyRoom studyRoomId, Date date, Long number, String start, String end, String detail) {
        this.studyRoomId = studyRoomId;
        this.date = date;
        this.number = number;
        this.start = start;
        this.end = end;
        this.detail = detail;
    }

    public void updatePostStudy(PostStudy postStudy) {
        this.date = postStudy.getDate();
        this.number = postStudy.getNumber();
        this.start = postStudy.getStart();
        this.end = postStudy.getEnd();
        this.detail = postStudy.getDetail();
    }
}
