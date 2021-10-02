package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.tuthree.domain.user.Teacher;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TeacherEntityRepository {

    private final EntityManager em;
    /**
     * 선생님 아이디가 들어왔을 때 찾는 로직
     */
    public Teacher findById(String id) {
        return em.find(Teacher.class, id);
    }
}
