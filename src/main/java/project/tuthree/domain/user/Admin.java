package project.tuthree.domain.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "admin_id")
    private String id;

    @Column(name = "admin_pwd")
    private String pwd;

}
