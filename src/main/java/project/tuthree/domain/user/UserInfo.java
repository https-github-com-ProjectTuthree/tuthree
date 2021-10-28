package project.tuthree.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;

import javax.persistence.*;
import java.util.List;

import static project.tuthree.domain.user.QUserInfo.userInfo;

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

    @Builder
    public UserInfo(String userId, String region, String subject) {
        this.userId = userId;
        this.region = region;
        this.subject = subject;
    }


    public void updateR(String userId, List<String> region) {
        this.userId = userId;
        for (String r : region){
            this.region = r;
        }

    }

    public void updateS(String  userId, List<String> subject){
        this.userId = userId;
        for (String s : subject){
            this.subject = s;
        }
    }
}
