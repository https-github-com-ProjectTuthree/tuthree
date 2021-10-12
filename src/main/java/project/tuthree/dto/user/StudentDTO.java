package project.tuthree.dto.user;

import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.School;
import project.tuthree.domain.user.Sex;
import project.tuthree.domain.user.User;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentDTO {

    private String id;
    private String pwd;
    private String name;
    private String email;
    private String tel;
    private Sex sex;
    private Integer birth;
    private String post;
    private Status notification;
    private Grade grade;
    private Date create_date;
    private String region;
    private Status registration;
    private String subject;
    private String cost;
    private School school;
    private String detail;
    private User user;

    @Builder
    public StudentDTO(String id, String pwd, String name, String email, String tel, Sex sex, Integer birth, String post, Status notification, Grade grade, Date create_date, String region, Status registration, String subject, String cost, School school, String detail, User user) {
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
}
