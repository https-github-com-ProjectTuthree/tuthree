package project.tuthree.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@NoArgsConstructor
public class StudentRegisterDTO extends UserRegisterDTO{

    @Builder
    public StudentRegisterDTO(String id, String pwd, String name, String email, String tel, Sex sex, int birth,
                   String post, Grade grade, String region, Status registration, String subject, int cost, School school, String detail) {
        super(id, pwd, name, email, tel, sex, birth, post, grade);
        this.region = region;
        this.registration = registration;
        this.subject = subject;
        this.cost = cost;
        this.school = school;
        this.detail = detail;
    }

    private String region; ///json
    private Status registration;
    private String subject;
    private int cost;
    private School school;
    private String detail;

    public Student toEntity() {
        super.toEntity();
        return Student.builder()
                .region(region)
                .registration(registration)
                .subject(subject)
                .cost(cost)
                .school(school)
                .detail(detail)
                .build();
    }

}
