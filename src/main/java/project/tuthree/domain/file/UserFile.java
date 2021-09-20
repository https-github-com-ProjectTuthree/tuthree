package project.tuthree.domain.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "file")
@SequenceGenerator(
        name = "FILE_SEQ_GENERATOR",
        sequenceName = "FILE_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class UserFile {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "FILE_SEQ_GENERATOR")
    @Column(name = "file_id")
    private Long id;

    //@ManyToOne
    //joinclumn을 어떻게 해야하나..
    @Column(name = "user_id")
    private String userId;

    @Column(name = "save_title")
    private String name1;

    @Column(name = "real_title")
    private String name2;
}
