package project.tuthree.domain.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.room.StudyRoomId;
import project.tuthree.domain.user.Teacher;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@IdClass(StudyRoomId.class)
@Table(name = "post_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReview implements Serializable {
    /**
     * 복합키 식별 관계 매핑
     */

    @Id
    @OneToOne(fetch = EAGER)
    @JoinColumns({
            @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"),
            @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    })
    private StudyRoom id;

    @Column(name = "post_star")
    private int star;

    @Column(name = "post_content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "write_at")
    private Date writeAt;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Builder
    public PostReview(StudyRoom id, int star, String content, Date writeAt) {
        this.id = id;
        this.star = star;
        this.content = content;
        this.writeAt = writeAt;
    }
}
