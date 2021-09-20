package project.tuthree.domain.post;

import lombok.Getter;
import project.tuthree.domain.file.UserFile;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "post_to_file")
@SequenceGenerator(
        name = "POSTFILE_SEQ_GENERATOR",
        sequenceName = "POSTFILE_SEQ",
        allocationSize = 1
)
public class PostToFile {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTFILE_SEQ_GENERATOR")
    @Column(name = "d_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "file_id")
    private UserFile file;
}
