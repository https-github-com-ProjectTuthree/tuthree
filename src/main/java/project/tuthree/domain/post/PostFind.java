package project.tuthree.domain.post;

import lombok.Getter;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    private LocalDateTime write_at;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime alter_at;

    @Enumerated(EnumType.ORDINAL)
    private Status secret;

}
