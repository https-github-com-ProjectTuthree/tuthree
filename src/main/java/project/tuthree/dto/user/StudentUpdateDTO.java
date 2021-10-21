package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.School;
import project.tuthree.domain.user.SchoolStatus;

import java.util.List;

@Getter
@NoArgsConstructor
public class StudentUpdateDTO {
    private String id;
    private List<String> regionL;
    private Status registration;
    private List<String> subjectL;
    private String cost;
    private School school;
    private String detail;

    @Builder
    public StudentUpdateDTO(String id, List<String> regionL, Status registration, List<String> subjectL,
                            String cost, School school, String detail) {
        this.id = id;
        this.regionL = regionL;
        this.registration = registration;
        this.subjectL = subjectL;
        this.cost = cost;
        this.school = school;
        this.detail = detail;
    }
}
