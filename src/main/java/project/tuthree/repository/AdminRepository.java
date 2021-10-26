package project.tuthree.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.domain.user.*;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static project.tuthree.domain.user.QAdmin.admin;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final int setPage = 3;


    public String findById(String id) {
        Admin admin = em.find(Admin.class, id);
        return admin.getId();
    }

    /** id, pwd 일치하는 관리자 찾기 */
    public Map<String, String> findByIdPwd(String id, String pwd) {
        Map<String, String> map = new HashMap<>();
        try {
            String s_pwd = jpaQueryFactory.select(admin.pwd)
                    .from(admin)
                    .where(admin.id.eq(id))
                    .fetchOne();
            if (bCryptPasswordEncoder.matches(pwd, s_pwd)) {
                map.put("name", "관리자");
                map.put("grade", Grade.ADMIN.getStrType());
            } else {
                map.put("grade", " ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }finally {
            return map;
        }
    }

    public List<User> userByPage(int page) {
        return em.createQuery("select u from User u order by u.createDate desc", User.class)
                .setFirstResult(setPage*(page-1))
                .setMaxResults(setPage)
                .getResultList();
    }

    public List<Teacher> teacherByPage(int page) {
        return em.createQuery("select t from Teacher t order by t.createDate desc", Teacher.class)
                .setFirstResult(setPage*(page-1))
                .setMaxResults(setPage)
                .getResultList();
    }

    public List<Student> studentByPage(int page) {
        return em.createQuery("select s from Student s order by s.createDate desc", Student.class)
                .setFirstResult(setPage*(page-1))
                .setMaxResults(setPage)
                .getResultList();
    }

    public Long userHasRow() {
        return (Long) em.createQuery("select count(u) from User u").getSingleResult()+(Long) em.createQuery("select count(t) from Teacher t").getSingleResult()+(Long) em.createQuery("select count(s) from Student s").getSingleResult();
    }

}
