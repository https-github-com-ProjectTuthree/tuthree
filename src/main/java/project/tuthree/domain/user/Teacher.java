package project.tuthree.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends User{

    public Teacher(String id, String pwd, String name, String email, String tel, Sex sex, int birth,
                   String post, Grade grade, Date create_date, String region, Status registration, String subject,
                   int cost, String school, SchoolStatus status, String major, String certification, boolean certifyStatus, String detail) {
        super(id, pwd, name, email, tel, sex, birth, post, grade, create_date);
        this.region = region;
        this.registration = registration;
        this.subject = subject;
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.certification = certification;
        this.certifyStatus = certifyStatus;
        this.detail = detail;
    }

    private String region; ///json

    @Enumerated(EnumType.ORDINAL)
    private Status registration;

    private String subject;

    private int cost;

    private String school;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "school_status")
    private SchoolStatus status;

    private String major;

    private String certification;

    @Column(name = "certify_status") //converter 적용하기
    private boolean certifyStatus;

    private String detail;

}
