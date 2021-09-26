package project.tuthree.domain.user;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Table(name = "Parent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User{

    @Id @Column(name = "user_id")
    private String id;

    @Column(name = "user_pwd")
    private String pwd;

    private String name;

    private String email;

    private String tel;

    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    private int birth;

    private String post;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

}
