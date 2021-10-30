package project.tuthree.dto.post;

import lombok.*;
import org.springframework.util.Assert;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.NoticeType;
import project.tuthree.domain.user.Admin;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class PostnoticeDTO {

    private Long id;

    @NotNull(message = "관리자 아이디 입력값 필요")
    private Admin adminId;

    @NotNull(message = "공지사항 타입 입력값 필요 : [NORMAL, IMPORTANT]")
    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @NotBlank(message = "공지사항 제목 입력값 필요")
    private String title;

    @NotBlank(message = "공지사항 내용 입력값 필요")
    private String content;

    private Long view = 0L;

    private Date writeAt = new Date(); //java.sql.timestamp??

    private Date alterAt;

    @NotNull(message = "공지사항 비밀글 여부 입력값 필요 : [OPEN, CLOSE]")
    private Status secret;

    @Builder
    public PostnoticeDTO(Long id, Admin adminId, NoticeType type, String title, String content,
                         Long view, Date writeAt, Date alterAt, Status secret) {
        Assert.notNull(adminId, "adminId must not be blank");
        Assert.notNull(type, "type must not be blank");
        Assert.hasText(title, "title must not be blank");
        Assert.hasText(content, "content must not be blank");
        //Assert.notNull(view, "view must not be blank");
        //Assert.notNull(writeAt, "writeAt must not be blank");
        Assert.notNull(secret, "secret must not be blank");
        this.id = id;
        this.adminId = adminId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.view = view;
        this.writeAt = writeAt;
        this.alterAt = alterAt;
        this.secret = secret;
    }

}
