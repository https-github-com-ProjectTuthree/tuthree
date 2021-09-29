package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student")
public class Student extends User{

    public Student(String id, String pwd, String name, String email, String tel, Sex sex, int birth, String post, Status notification,
                   Grade grade, Date create_date, String region, Status registration, String subject, int cost, School school, String detail, User parent) {
        super(id, pwd, name, email, tel, sex, birth, post, notification, grade, create_date);
        this.region = region;
        this.registration = registration;
        this.subject = subject;
        this.cost = cost;
        this.school = school;
        this.detail = detail;
        this.parent = parent;
    }

    private String region; ///json

    @Enumerated(EnumType.ORDINAL)
    private Status registration;

    private String subject;

    private int cost;

    @Enumerated(EnumType.STRING)
    private School school;

    private String detail;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;

}
