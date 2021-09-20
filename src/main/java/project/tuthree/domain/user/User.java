package project.tuthree.domain.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name = "member_type")
@Getter
@Table(name = "Parent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User extends Admin{

    @Builder
    public User(String id, String pwd, String name, Mail mail, Tel tel, Sex sex, int birth, String post, Grade grade, Date create_date) {
        super(id, pwd);
        this.name = name;
        this.mail = mail;
        this.tel = tel;
        this.sex = sex;
        this.birth = birth;
        this.post = post;
        this.grade = grade;
        this.create_date = create_date;
    }

    private String name;

    @Embedded
    private Mail mail;

    @Embedded
    private Tel tel;

    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    private int birth;

    private String post;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Temporal(TemporalType.DATE)
    private Date create_date;

}
