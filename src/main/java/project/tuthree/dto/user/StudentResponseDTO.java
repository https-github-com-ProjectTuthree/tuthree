package project.tuthree.dto.user;

import lombok.Getter;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;
import java.util.List;

@Getter
public class StudentResponseDTO {
    private String id;
    private List<String> region; ///json
    private Status registration;
    private List<String> subject;
    private String cost;
    private School school;
    private String detail;

    public StudentResponseDTO(Student entity, List<String> region, List<String> subject) {
        this.id = entity.getId();
        this.registration = entity.getRegistration();
        this.cost = entity.getCost();
        this.school = entity.getSchool();
        this.detail = entity.getDetail();
        this.region = region;
        this.subject = subject;
    }
}
