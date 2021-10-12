package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.SchoolStatus;
import project.tuthree.domain.user.Teacher;

@Getter
@NoArgsConstructor
public class TeacherUpdateDTO {
    private String region; ///json
    private Status registration;
    private String subject;
    private String cost;
    private String school;
    private SchoolStatus status;
    private String major;
    private String detail;

    @Builder
    public TeacherUpdateDTO(String region, Status registration, String subject,
                            String cost, String school, SchoolStatus status, String major,String detail) {
        this.region = region;
        this.registration = registration;
        this.subject = subject;
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.detail = detail;
    }
}
