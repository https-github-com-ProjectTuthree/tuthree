package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Enumerated(EnumType.STRING)
    private Status registration;

    @Column(name = "user_cost")
    private String cost;

    private String school;

    @Enumerated(EnumType.STRING)
    @Column(name = "school_status")
    private SchoolStatus status;

    private String major;

    @Column(name = "user_star")
    private double star;

    private String certification;

    //@Convert(converter=BooleanToYNConverter.class) //db에 데이터타입이 이미 0,1이라 데이터타입을 바꿔야될듯
    @Column(name = "certify_status") //converter 적용하기
    private boolean certifyStatus;

    private String detail;

    @Column(name= "user_del")
    @Enumerated(EnumType.STRING)
    private Status userDel;

    @Override
    public boolean isNew(){
        return createDate == null;
    }

    @Builder
    public Teacher(String id, String pwd, String name, String email, String tel, Sex sex, Integer birth, String post, Status notification,
                   Grade grade, Date createDate, Status registration, String cost, String school, SchoolStatus status, String major,
                   double star, String certification, boolean certifyStatus, String detail, Status userDel) {
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
        this.createDate = createDate;
        this.registration = registration;
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.star = star;
        this.certification = certification;
        this.certifyStatus = certifyStatus;
        this.detail = detail;
        this.userDel = userDel;
    }

    public void update(Status registration, String cost, String school,
                       SchoolStatus status, String major, String detail, String certification){
        this.registration = registration;
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.detail = detail;
        this.certification = certification;
    }

    public void updateInfo(String email, String tel,Integer birth, String post, Status notification){
        this.email = email;
        this.tel = tel;
        this.birth = birth;
        this.post = post;
        this.notification = notification;
    }

    public void updateStar(Long num, int star) {
        double newStar = (this.star * (num - 1) + star) / num;
        this.star = (double) Math.round(newStar * 10) / 10;
    }

    public void updateP(String pwd){
        this.pwd =new BCryptPasswordEncoder().encode(pwd);
    }

    public void updateAuth(){
        this.certifyStatus = true;
    }

    public void userDel(){this.userDel = Status.CLOSE;}
}