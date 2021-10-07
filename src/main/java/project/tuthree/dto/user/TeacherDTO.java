package project.tuthree.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.SchoolStatus;
import project.tuthree.domain.user.Sex;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherDTO {

    @NotNull
    private String id;

    @NotNull
    private String pwd;

    @NotNull
    private String name;

    private String email;

    @NotNull
    private String tel;

    @NotNull
    private Sex sex;

    @NotNull
    private int birth;

    private String post;

    @NotNull
    private Status notification;

    @NotNull
    private Grade grade;

    @NotNull
    private Date create_date;

    @NotNull
    private String region;

    @NotNull
    private Status registration;

    @NotNull
    private String subject;

    @NotNull
    private int cost;

    @NotNull
    private String school;

    @NotNull
    private SchoolStatus status;

    private String major;

    private double star;

    @NotNull
    private String certification;

    @NotNull
    private boolean certifyStatus;

    @NotNull
    private String detail;

    @Builder
    public TeacherDTO(String id, String pwd, String name, String email, String tel, Sex sex, int birth, String post, Status notification, Grade grade, Date create_date, String region, Status registration, String subject, int cost, String school, SchoolStatus status, String major, double star, String certification, boolean certifyStatus, String detail) {
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
        this.status = status;
        this.major = major;
        this.star = star;
        this.certification = certification;
        this.certifyStatus = certifyStatus;
        this.detail = detail;
    }
}
