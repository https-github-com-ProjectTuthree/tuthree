package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.SchoolStatus;
import project.tuthree.domain.user.Sex;
import project.tuthree.domain.user.Teacher;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
public class TeacherResponseDTO {

    private String id;
    private List<String> region;
    private Status registration;
    private List<String> subject;
    private String cost;
    private String school;
    private SchoolStatus status;
    private String major;
    private String certification;
    private String detail;

    public TeacherResponseDTO(Teacher entity, List<String> region, List<String> subject) {
        this.id = entity.getId();
        this.registration = entity.getRegistration();
        this.cost = entity.getCost();
        this.school = entity.getSchool();
        this.status = entity.getStatus();
        this.major = entity.getMajor();
        this.certification = entity.getCertification();
        this.detail = entity.getDetail();
        this.region = region;
        this.subject = subject;
    }

}
