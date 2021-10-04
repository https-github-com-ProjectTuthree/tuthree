package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.School;
import project.tuthree.domain.user.SchoolStatus;

@Getter
@NoArgsConstructor
public class StudentUpdateDTO {
    private String region; ///json
    private Status registration;
    private String subject;
    private Integer cost;
    private School school;
    private String detail;

    @Builder
    public StudentUpdateDTO(String region, Status registration, String subject,
                            Integer cost, School school, String detail) {
        this.region = region;
        this.registration = registration;
        this.subject = subject;
        this.cost = cost;
        this.school = school;
        this.detail = detail;
    }
}
