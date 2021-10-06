package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostFind;
import project.tuthree.domain.post.QPostFind;
import project.tuthree.domain.user.Teacher;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostFindRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

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
     * id로 특정 게시글 조회
     */
    public PostFind findById(Long postId) {
        return em.find(PostFind.class, postId);
    }

    /** 선생님 id로 특정 게시글 조회 */
    public PostFind findByUserTeacherId(String userId) {
        return jpaQueryFactory.selectFrom(QPostFind.postFind)
                .where(QPostFind.postFind.teacherId.id.eq(userId))
                .fetchOne();
    }

    /** 선생님 id로 */

}
