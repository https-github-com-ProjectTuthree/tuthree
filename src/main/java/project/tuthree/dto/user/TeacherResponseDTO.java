package project.tuthree.dto.user;

import lombok.Getter;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.SchoolStatus;
import project.tuthree.domain.user.Teacher;

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
    private boolean certifyStatus;
    private String detail;
    private byte[] file;

    public TeacherResponseDTO(Teacher entity, List<String> region, List<String> subject, byte[] file) {
        this.id = entity.getId();
        this.registration = entity.getRegistration();
        this.cost = entity.getCost();
        this.school = entity.getSchool();
        this.status = entity.getStatus();
        this.major = entity.getMajor();
        this.certification = entity.getCertification();
        this.detail = entity.getDetail();
        this.certifyStatus = entity.isCertifyStatus();
        this.region = region;
        this.subject = subject;
        this.file = file;
    }

}
