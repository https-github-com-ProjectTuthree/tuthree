package project.tuthree.domain.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "admins")
public class Admin {

    @Id
    @Column(name = "user_id")
    private String id;

    private String pwd;

}
