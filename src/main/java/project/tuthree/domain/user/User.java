package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.tuthree.domain.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Table(name = "parent")
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "user_pwd")
    @JsonIgnore
    private String pwd;

    @Column(name = "user_name")
    private String name;

    private String email;

    private String tel;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private int birth;

    private String post;

    @Column(name = "user_notification")
    private Status notification;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

   @Builder
    public User(String id, String pwd, String name, String email, String tel, Sex sex, int birth, String post, Status notification, Grade grade, Date create_date) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.sex = sex;
        this.birth = birth;
        this.post = post;
        this.notification = notification;
        this.grade = grade;
        this.create_date = create_date;
    }
}
