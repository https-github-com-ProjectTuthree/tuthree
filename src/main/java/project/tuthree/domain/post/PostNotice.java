package project.tuthree.domain.post;

import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Admin;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "POSTNOTICE_SEQ_GENERATOR",
        sequenceName = "POSTNOTICE_SEQ",
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostNotice extends PostAdmin{

    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @Builder
    public PostNotice(Admin admin, String title, String content,
                      Long view, Date writeAt, Date alterAt, Status secret, NoticeType type) {
        super(admin, title, content, view, writeAt, alterAt, secret);
        this.type = type;
    }
}
