package project.tuthree.dto;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.FaqType;
import project.tuthree.domain.user.Admin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@ToString
@Setter
public class PostfaqDTO {
    //id처리를 어떻게 하는가? 그냥 entity에서도 아이디 안적어도 알아서 들어가는것?

    private Long id;
    @NotNull
    private Admin adminId;
    @NotNull
    private FaqType type;
    @NotNull
    private String title;
    @NotNull
    private String content;

    @ColumnDefault("0")
    private Long view;

    private Date writeAt;//java.sql.timeastamp

    private Date alterAt;

    @NotNull
    private Status secret;

    @Builder
    public PostfaqDTO(Long id, Admin adminId, FaqType type, String title, String content,
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
