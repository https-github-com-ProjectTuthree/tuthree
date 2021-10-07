package project.tuthree.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.room.StudyRoomInfo;

import javax.persistence.EntityManager;

@Getter
@Repository
@RequiredArgsConstructor
public class StudyRoomRepository {

    private final EntityManager em;

    /** 스터디룸 개설하기 */
    public String roomRegister(StudyRoom studyRoom) {
        em.persist(studyRoom);
        return studyRoom.getTeacherId().getId();
    }

    /** 수업 계획서 등록하기 */
    public String infoRegister(StudyRoomInfo studyRoomInfo) {
        em.persist(studyRoomInfo);
        return studyRoomInfo.getId().getTeacherId().getId();
    }
}
