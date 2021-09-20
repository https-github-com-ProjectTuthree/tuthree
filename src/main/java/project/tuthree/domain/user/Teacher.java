package project.tuthree.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends User{

    @Builder
    public Teacher(String id, String pwd, String name, Mail mail, Tel tel, Sex sex, int birth,
                   String post, Grade grade, LocalDateTime create_date, Status registration,
                   String region, String subject, int cost, String detail, String school,
                   SchoolStatus status, String major) {
        super(id, pwd, name, mail, tel, sex, birth, post, grade, create_date);
        this.registration = registration;
        this.region = region;
        this.subject = subject;
        this.cost = cost;
        this.detail = detail;
        this.school = school;
        this.status = status;
        this.major = major;
    }

    @Enumerated(EnumType.ORDINAL)
    private Status registration;

    private String region; ///json

    private String subject;

    private int cost;

    private String detail;

    private String school;

    @Enumerated(EnumType.ORDINAL)
    private SchoolStatus status;

    private String major;
}
