package project.tuthree.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import project.tuthree.domain.room.StudyRoom;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@SequenceGenerator(
        name = "POSTSTUDY_SEQ_GENERATOR",
        sequenceName = "POSTSTUDY_SEQ",
        allocationSize = 1
)
public class PostStudy {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTSTUDY_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"),
            @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    })
    private StudyRoom studyRoomId;
}
