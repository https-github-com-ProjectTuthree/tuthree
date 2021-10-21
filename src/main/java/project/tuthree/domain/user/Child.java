package project.tuthree.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(
        name = "CHILD_SEQ_GENERATOR",
        sequenceName = "CHILD_SEQ",
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "child")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CHILD_SEQ_GENERATOR")
    private Long id;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "student_id")
    private String studentId;
    @Column(name = "student_name")
    private String studentName;

    private boolean status;

    @Builder
    public Child(Long id, String parentId, String studentId, String studentName, boolean status){
        this.id = id;
        this.studentId = studentId;
        this.parentId = parentId;
        this.studentName = studentName;
        this.status = status;
    }

    public void accept(){
        this.status = true;
    }
}
