package project.tuthree.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.School;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentUpdateDTO {
    private List<String> regionL;
    private Status registration;
    private List<String> subjectL;
    private String cost;
    private School school;
    private String detail;

    @Builder
    public StudentUpdateDTO(List<String> regionL, Status registration, List<String> subjectL,
                            String cost, School school, String detail) {
        this.regionL = regionL;
        this.registration = registration;
        this.subjectL = subjectL;
        this.cost = cost;
        this.school = school;
        this.detail = detail;
    }
}
