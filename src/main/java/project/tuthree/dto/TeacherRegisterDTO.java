package project.tuthree.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@NoArgsConstructor
public class TeacherRegisterDTO extends UserRegisterDTO {

    @Builder
    public TeacherRegisterDTO(String id, String pwd, String name, String email, String tel, Sex sex, int birth,
                              String post, Grade grade, String region, Status registration, String subject,
                              int cost, String school, SchoolStatus status, String major, String certification, String detail) {
        super(id, pwd, name, email, tel, sex, birth, post, grade);
        this.region = region;
        this.registration = registration;
        this.subject = subject;
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.certification = certification;
        this.detail = detail;
    }

    private String region; ///json
    private Status registration;
    private String subject;
    private int cost;
    private String school;
    private SchoolStatus status;
    private String major;
    private String certification;
    private String detail;


    public Teacher toEntity() {
        super.toEntity();
        return Teacher.builder()
                .region(region)
                .registration(registration)
                .subject(subject)
                .cost(cost)
                .school(school)
                .status(status)
                .major(major)
                .certification(certification)
                .detail(detail)
                .build();
    }

}
