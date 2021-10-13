package project.tuthree.domain.user;

import lombok.Getter;

@Getter
public enum SchoolStatus {
    IN_SCHOOL("재학 상태"),
    ABSENCE_OF_SCHOOL("휴학 상태"),
    GRADUATE("졸업 상태");

    private String kortype;

    SchoolStatus(String kortype) {
        this.kortype = kortype;
    }
}
