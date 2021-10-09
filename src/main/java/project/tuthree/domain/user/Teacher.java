package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import project.tuthree.domain.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "teacher")
public class Teacher implements Persistable<String>{

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

    private Integer birth;

    private String post;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_notification")
    private Status notification;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

    @Enumerated(EnumType.STRING)
    private Status registration;

    @Column(name = "user_cost")
    private Integer cost;

    private String school;

    @Enumerated(EnumType.STRING)
    @Column(name = "school_status")
    private SchoolStatus status;

    private String major;

    @Column(name = "user_star")
    private double star;

    private String certification;

    @Column(name = "certify_status") //converter 적용하기
    private boolean certifyStatus;

    private String detail;

    @Override
    public boolean isNew(){
        return create_date == null;
    }


    @Builder
    public Teacher(String id, String pwd, String name, String email, String tel, Sex sex, Integer birth, String post, Status notification,
                   Grade grade, Date create_date, Status registration, Integer cost, String school, SchoolStatus status, String major,
                   double star, String certification, boolean certifyStatus, String detail) {
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
        this.registration = registration;
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.star = star;
        this.certification = certification;
        this.certifyStatus = certifyStatus;
        this.detail = detail;
    }

    public void update(Status registration, Integer cost, String school,
                       SchoolStatus status, String major, String detail){
        this.registration = registration;
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.detail = detail;
    }
}

