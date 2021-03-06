package project.tuthree.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Teacher;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTestPaperDTO {

    private Long id;

    @NotNull
    private Teacher userId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private Long view;

    private Date writeAt;

    private Date alterAt;

    @NotNull
    private Status secret;

    @Builder
    public PostTestPaperDTO(Long id, Teacher userId, String title, String content,
                            Long view, Date writeAt, Date alterAt, Status secret) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.view = view;
        this.writeAt = writeAt;
        this.alterAt = alterAt;
        this.secret = secret;
    }

    public void testpaperWriteAt() {
        this.writeAt = new Date();
    }


}
