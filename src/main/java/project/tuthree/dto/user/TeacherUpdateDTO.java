package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.SchoolStatus;
import project.tuthree.domain.user.Teacher;

import java.util.List;

@Getter
@NoArgsConstructor
public class TeacherUpdateDTO {
    private String id;
    private List<String> regionL;
    private Status registration;
    private List<String> subjectL;
    private String cost;
    private String school;
    private SchoolStatus status;
    private String major;
    private String detail;

    @Builder
    public TeacherUpdateDTO(String id, List<String> regionL, Status registration, List<String> subjectL,
                            String cost, String school, SchoolStatus status, String major,String detail) {
        this.id = id;
        this.regionL = regionL;
        this.registration = registration;
        this.subjectL = subjectL;
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.detail = detail;
    }
}
