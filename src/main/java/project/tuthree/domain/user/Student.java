package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student")
public class Student{

    @Builder
    public Student(String id, String pwd, String name, String email, String tel, Sex sex, int birth, String post, Status notification,
                   Grade grade, Date create_date, String region, Status registration, String subject, int cost, School school, String detail, User user) {
        //super(id, pwd, name, email, tel, sex, birth, post, notification, grade, create_date);
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
        this.region = region;
        this.registration = registration;
        this.subject = subject;
        this.cost = cost;
        this.school = school;
        this.detail = detail;
        this.user = user;
    }
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

    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    private int birth;

    private String post;

    @Column(name = "user_notification")
    private Status notification;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

    private String region; ///json

    @Enumerated(EnumType.ORDINAL)
    private Status registration;

    private String subject;

    @Column(name = "user_cost")
    private int cost;

    @Enumerated(EnumType.STRING)
    private School school;

    private String detail;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User user;

}
