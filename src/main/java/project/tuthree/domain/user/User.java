package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.NodeBuilder;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Table(name = "Parent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "user_pwd")
    @JsonIgnore
    private String pwd;

    private String name;

    private String email;

    private String tel;

    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    private int birth;

    private String post;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

    @Builder
    public User(String id, String pwd, String name, String email, String tel, Sex sex, int birth,String post, Grade grade, Date create_date){
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.sex = sex;
        this.birth = birth;
        this.post = post;
        this.grade = grade;
        this.create_date = create_date;
    }
}
