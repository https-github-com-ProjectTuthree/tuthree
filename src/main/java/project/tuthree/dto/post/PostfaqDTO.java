package project.tuthree.dto.post;

import lombok.*;
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
    @NotNull(message = "FAQ 작성자 아이디 입력값 필요")
    private Admin adminId;

    @NotNull(message = "FAQ 타입 입력값 필요")
    @Enumerated(EnumType.STRING)
    private FaqType type;

    @NotBlank(message = "FAQ 제목 입력값 필요")
    private String title;
    @NotBlank(message = "FAQ 내용 입력값 필요")
    private String content;

    private Long view = 0L;

    private Date writeAt = new Date();

    private Date alterAt;

    @NotNull(message = "FAQ 비밀글 여부 입력값 필요")
    private Status secret;

    @Builder
    public PostfaqDTO(Long id, Admin adminId, FaqType type, String title, String content, Status secret) {
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
