package project.tuthree.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.SchoolStatus;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherUpdateDTO {
    private List<String> regionL;
    private Status registration;
    private List<String> subjectL;
    private String cost;
    private String school;
    private SchoolStatus status;
    private String major;
    private String detail;
    private MultipartFile authFile;
    private String certification;

    @Builder
    public TeacherUpdateDTO(List<String> regionL, Status registration, List<String> subjectL,
                            String cost, String school, SchoolStatus status, String major,String detail, MultipartFile authFile, String certification) {
        this.regionL = regionL;
        this.registration = registration;
        this.subjectL = subjectL;
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.detail = detail;
        this.authFile = authFile;
        this.certification = certification;
    }
    public void updateAuthPost(String post) {
        this.certification = post;
    }}
