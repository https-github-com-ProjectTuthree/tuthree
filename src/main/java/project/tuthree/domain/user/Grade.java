package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Grade {
    PARENT("parent"),
    STUDENT("student"),
    TEACHER("teacher"),
    ADMIN("admin");

    private String strType;

    Grade(String strType) {
        this.strType = strType;
    }
}
