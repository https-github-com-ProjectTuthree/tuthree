package project.tuthree.domain.post;

import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Admin;

import javax.persistence.*;

import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostFaq extends PostAdmin{

    @Enumerated(EnumType.STRING)
    private FaqType type;

    @Builder
    public PostFaq(Admin admin, String title, String content,
                   Long view, Date writeAt, Date alterAt, Status secret, FaqType type) {
        super(admin, title, content, view, writeAt, alterAt, secret);
        this.type = type;
    }


}
