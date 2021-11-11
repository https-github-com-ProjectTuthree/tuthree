package project.tuthree.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.StudyRoom;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostreviewDTO {


    private StudyRoom id;

    @NotNull(message = "평점 입력값 필요")
    @Min(value = 0)
    @Max(value = 5)
    private int star;

    @NotBlank(message = "후기 내용 입력값 필요")
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
