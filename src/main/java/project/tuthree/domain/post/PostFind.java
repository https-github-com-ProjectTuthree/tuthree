package project.tuthree.domain.post;

import lombok.Getter;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
public class PostFind implements Serializable {

    @Id
    @OneToOne //FETCH??
    @JoinColumn(name = "user_id")
    private User id;

    private String title;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date write_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alter_at;

    @Enumerated(EnumType.ORDINAL)
    private Status secret;

}
