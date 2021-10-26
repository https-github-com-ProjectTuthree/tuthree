package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import project.tuthree.domain.user.Child;

import static java.lang.Boolean.FALSE;

@Getter
@Setter
@NoArgsConstructor
public class ChildDTO {
    private String parentId;
    private String studentId;
    private String studentName;

    @ColumnDefault("FALSE")
    private boolean status;

    @Builder
    public ChildDTO(String parentId, String studentId, String studentName, boolean status){
        this.parentId = parentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.status = status;
    }

    public Child toEntity() {
        return Child.builder()
                .parentId(parentId)
                .studentId(studentId)
                .studentName(studentName)
                .status(status)
                .build();
    }

}
