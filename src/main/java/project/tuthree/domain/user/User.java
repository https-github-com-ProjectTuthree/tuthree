package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.tuthree.domain.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "parent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Persistable<String>{

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

    @Column(name = "user_notification")
    @Enumerated(EnumType.STRING)
    private Status notification;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @CreatedDate
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name= "user_del")
    @Enumerated(EnumType.STRING)
    private Status userDel;



    @Override
    public boolean isNew(){
        return createDate == null;
    }

    public interface Persistable<ID>{
        ID getId();
        boolean isNew();
    }

   @Builder
    public User(String id, String pwd, String name, String email, String tel, Sex sex, Integer birth, String post, Status notification, Grade grade, Date createDate, Status userDel) {
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
        this.userDel = userDel;
    }

    public void updateInfo(String email, String tel,Integer birth, String post, Status notification){
        this.email = email;
        this.tel = tel;
        this.birth = birth;
        this.post = post;
        this.notification = notification;
    }

    public void updateP(String pwd){
        this.pwd = new BCryptPasswordEncoder().encode(pwd);
    }

    public void userDel(){this.userDel = Status.CLOSE;}
}

