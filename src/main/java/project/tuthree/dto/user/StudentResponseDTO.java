package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
public class StudentResponseDTO {
    private String id;
    private String region; ///json
    private Status registration;
    private String subject;
    private int cost;
    private School school;
    private String detail;

    public StudentResponseDTO(Student entity) {
        this.id = entity.getId();
        this.region = entity.getRegion();
        this.registration = entity.getRegistration();
        this.subject = entity.getSubject();
        this.cost = entity.getCost();
        this.school = entity.getSchool();
        this.detail = entity.getDetail();
    }
}
