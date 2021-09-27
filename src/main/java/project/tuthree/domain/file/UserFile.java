package project.tuthree.domain.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.tuthree.domain.post.PostTestPaper;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.room.StudyRoomId;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "file")
@SequenceGenerator(
        name = "FILE_SEQ_GENERATOR",
        sequenceName = "FILE_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class UserFile {
    /**
     * 비식별 복합키 매핑 : idclass 방식
     */

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "FILE_SEQ_GENERATOR")
    @Column(name = "file_id")
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"),
            @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    })
    private StudyRoom studyRoomId;
    //스터디룸 복합키 비식별 관계 매핑

    @Column(name = "save_title")
    private String saveTitle;

    @Column(name = "real_title")
    private String realTitle;

    @ManyToOne
    @JoinColumn(name = "testpaper_id")
    private PostTestPaper testpaperId;


}
