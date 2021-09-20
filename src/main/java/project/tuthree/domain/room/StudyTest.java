package project.tuthree.domain.room;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class StudyTest {
    @Id
    @Column(name = "d_id")
    private Long id;

    private Long room_id;

    private Long file_id;
}
