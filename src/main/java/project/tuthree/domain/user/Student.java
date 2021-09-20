package project.tuthree.domain.user;

import javafx.scene.Parent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
//@DiscriminatorColumn(name = "student")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends User {

    @Builder
    public Student(String id, String pwd, String name, Mail mail, Tel tel, Sex sex, int birth,
                   String post, Grade grade, LocalDateTime create_date, Status registration,
                   String region, String subject, int cost, String detail, School school, List<User> parent) {
        super(id, pwd, name, mail, tel, sex, birth, post, grade, create_date);
        this.registration = registration;
        this.region = region;
        this.subject = subject;
        this.cost = cost;
        this.detail = detail;
        this.school = school;
        this.parent = parent;
    }

    @Enumerated(EnumType.ORDINAL)
    private Status registration;

    private String region; ///json

    private String subject;

    private int cost;

    private String detail;

    @Enumerated(EnumType.STRING)
    private School school;

    @OneToMany(mappedBy = "parent")///////////////////////////////////////////////////
    @JoinColumn(name = "user_id")
    private List<User> parent;


}
