package project.tuthree.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@SequenceGenerator(
        name = "LOG_SEQ_GENERATOR",
        sequenceName = "LOG_SEQ",
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Log {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "LOG_SEQ_GENERATOR")
    @Column(name = "log_id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)//time?timestamp?
    private Date time;

    @Column(name = "ip_addr")
    private String ipAddr;
    //httpservletrequest 또는 requestbody를 공부한다음 다시 만들기

    @Column(name = "log_type")
    private int logType;

    @Column(name = "user_id")
    private String userId;

}
