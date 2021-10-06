package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.Category;
import project.tuthree.domain.user.Teacher;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostFindRepository {

    private EntityManager em;
    /**
     * 선생님 목록 조회
     */
    public List<Teacher> findByPage(int page) {
        int setPage = 10;
        return em.createQuery("select t from Teacher t", Teacher.class)
                .setFirstResult(setPage * (page - 1))
                .setMaxResults(setPage)
                .getResultList();
    }

    /**
     * 특정 선생님 조회
     */
//    public Teacher findById(Long postId) {
//        Teacher
//
//    }

}
