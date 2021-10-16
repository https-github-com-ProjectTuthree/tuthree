package project.tuthree.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Admin;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@SequenceGenerator(
        name = "POSTADMIN_SEQ_GENERATOR",
        sequenceName = "POSTADMIN_SEQ",
        allocationSize = 1
)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public abstract class PostAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTADMIN_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private String title;

    private String content;

    private Long view;

    @Temporal(TemporalType.DATE)
    @Column(name = "write_at")
    private Date writeAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "alter_at")
    private Date alterAt;

    private Status secret;

    public PostAdmin(Admin admin, String title, String content,
                     Long view, Date writeAt, Date alterAt, Status secret) {
        this.admin = admin;
        this.title = title;
        this.content = content;
        this.view = view;
        this.writeAt = writeAt;
        this.alterAt = alterAt;
        this.secret = secret;
    }
}
