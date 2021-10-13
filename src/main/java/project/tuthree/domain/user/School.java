package project.tuthree.domain.user;

import lombok.Getter;

import java.util.Map;

@Getter
public enum School {
    UNDER_MIDDLE("중학교 입학 전"),
    M1("중학교 1학년"),
    M2("중학교 2학년"),
    M3("중학교 3학년"),
    H1("고등학교 1학년"),
    H2("고등학교 2학년"),
    H3("고등학교 3학년"),
    OVER_HIGH("고등학교 졸업"),
    EXAM_M("중학교 검정고시 준비"),
    EXAM_H("고등학교 검정고시 준비");

    private String korStatus;

    School(String korStatus) {
        this.korStatus = korStatus;
    }

    public Map<String, School> statusOf() {
        Map<String, School> map = null;
        for (School school : School.values()) {
            map.put("학력", school);
        }
        return map;
    }
}
