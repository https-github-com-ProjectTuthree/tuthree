package project.tuthree.dto;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.NoticeType;
import project.tuthree.domain.user.Admin;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class PostnoticeDTO {

    private Long id;

    @NotNull
    private Admin adminId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @ColumnDefault("0")
    private Long view;

    private Date writeAt = new Date(); //java.sql.timestamp??

    private Date alterAt;

    @NotNull
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

    public void noticeWriteAt() {
        this.writeAt = new Date();
    }

    public void noticeAlterAt() {
        this.alterAt = new Date();
    }
}
