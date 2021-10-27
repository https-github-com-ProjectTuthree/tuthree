package project.tuthree.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.room.StudyRoomId;
import project.tuthree.domain.room.StudyRoomInfo;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.SchoolStatus;
import project.tuthree.domain.user.Sex;
import project.tuthree.domain.user.Teacher;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
    private Date createDate;

    @NotNull
    private String region;

    private List<String> regionL;

    @NotNull
    private Status registration;

    @NotNull
    private String subject;

    private List<String> subjectL;

    @NotNull
    private String cost;

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

    private List<StudyRoom> studentsIng;

    private List<StudyRoom> studentsEnd;

    private byte[] file;

    private byte[] authFile;

    private Status userDel;

    @Builder
    public TeacherDTO(String id, String pwd, String name, String email, String tel, Sex sex, int birth, String post, Status notification, Grade grade, Date createDate, String region, Status registration, String subject, String cost, String school, SchoolStatus status, String major, double star, String certification, boolean certifyStatus, String detail, Status userDel) {
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
        this.userDel = userDel;
    }

    public TeacherDTO(Teacher entity, List<String> regionL, List<String> subjectL, List<StudyRoom> studentsIng, List<StudyRoom> studentsEnd, byte[] file, byte[] authFile) {
        this.id = entity.getId();
        this.pwd = entity.getPwd();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
        this.birth = entity.getBirth();
        this.post = entity.getPost();
        this.notification = entity.getNotification();
        this.grade = entity.getGrade();
        this.createDate = entity.getCreateDate();
        this.star = entity.getStar();
        this.registration = entity.getRegistration();
        this.cost = entity.getCost();
        this.school = entity.getSchool();
        this.status = entity.getStatus();
        this.major = entity.getMajor();
        this.certification = entity.getCertification();
        this.detail = entity.getDetail();
        this.userDel = entity.getUserDel();
        this.regionL = regionL;
        this.subjectL = subjectL;
        this.studentsIng = studentsIng;
        this.studentsEnd = studentsEnd;
        this.file = file;
        this.authFile = authFile;
    }
}
