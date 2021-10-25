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
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student")
public class Student implements Persistable<String>{

    @Builder
    public Student(String id, String pwd, String name, String email, String tel, Sex sex, Integer birth, String post,
                   Status notification, Grade grade, Date createDate, Status registration, String cost, School school, String detail, User user) {
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

//    private String region; ///json

    @Enumerated(EnumType.STRING)
    private Status registration;

//    private String subject;

    @Column(name ="user_cost")
    private String cost;

    @Enumerated(EnumType.STRING)
    private School school;

    private String detail;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User user;

    @Override
    public boolean isNew(){
        return createDate == null;
    }

    public void update(Status registration, String cost, School school, String detail) {
        this.registration = registration;
        this.cost = cost;
        this.school = school;
        this.detail = detail;
    }
    public void updateInfo(String email, String tel,Integer birth, String post, Status notification){
        this.email = email;
        this.tel = tel;
        this.birth = birth;
        this.post = post;
        this.notification = notification;
    }

    public void accept(User user){
        this.user = user;
    }

    public void updateP(String pwd){
        this.pwd = new BCryptPasswordEncoder().encode(pwd);
    }
}

