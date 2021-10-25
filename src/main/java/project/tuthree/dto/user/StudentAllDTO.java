package project.tuthree.dto.user;

import lombok.*;
import project.tuthree.domain.Status;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.user.*;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentAllDTO {

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
    private Date createDate;
    private List<String> regionL;
    private Status registration;
    private List<String> subjectL;
    private String cost;
    private School school;
    private String detail;
    private User user;
    private List<StudyRoom> teachersIng;
    private List<StudyRoom> teachersEnd;
    private byte[] file;


    public StudentAllDTO(Student entity, List<String> regionL, List<String> subjectL, List<StudyRoom> teachersIng, List<StudyRoom> teachersEnd, byte[] file) {
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
        this.registration = entity.getRegistration();
        this.cost = entity.getCost();
        this.school = entity.getSchool();
        this.detail = entity.getDetail();
        this.regionL = regionL;
        this.subjectL = subjectL;
        this.teachersIng = teachersIng;
        this.teachersEnd = teachersEnd;
        this.user = entity.getUser();
        this.file = file;
    }



}
