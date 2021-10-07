package project.tuthree.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.StudyRoom;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostreviewDTO {

    private StudyRoom id;

    private int star;

    private String content;

    private Date writeAt;

    @Builder
    public PostreviewDTO(StudyRoom id, int star, String content, Date writeAt) {
        this.id = id;
        this.star = star;
        this.content = content;
        this.writeAt = writeAt;
    }

    public void updateId(StudyRoom studyRoom) {
        this.id = studyRoom;
        this.writeAt = new Date();
    }
}
