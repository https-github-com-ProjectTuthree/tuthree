package project.tuthree.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "user_info")
@SequenceGenerator(
        name = "USERIFNO_SEQ_GENERATOR",
        sequenceName = "USERINFO_SEQ",
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "USERIFNO_SEQ_GENERATOR")
    @Column(name = "d_id")
    int id;

    @Column(name = "user_id")
    String userId;

    String region;

    String subject;
}
