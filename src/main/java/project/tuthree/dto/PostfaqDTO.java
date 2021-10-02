package project.tuthree.dto;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.FaqType;
import project.tuthree.domain.user.Admin;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class PostfaqDTO {

    private Long id;
    @NotNull
    private Admin adminId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FaqType type;
    @NotNull
    private String title;
    @NotNull
    private String content;

    private Long view = 0L;

    private Date writeAt = new Date();

    private Date alterAt;

    @NotNull
    private Status secret;

    @Builder
    public PostfaqDTO(Long id, Admin adminId, FaqType type, String title, String content, Status secret) {
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
        this.view = 0L;
        this.writeAt = new Date();
        this.secret = secret;
    }
}
