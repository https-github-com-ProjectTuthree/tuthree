package project.tuthree.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.SchoolStatus;
import project.tuthree.domain.user.Sex;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@AllArgsConstructor
@Setter
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

    @NotNull
    private String certification;

    @NotNull
    private boolean certifyStatus;

    @NotNull
    private String detail;
}
