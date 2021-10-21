package project.tuthree.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.Admin;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.QAdmin;

import javax.persistence.EntityManager;

import static project.tuthree.domain.user.QAdmin.admin;

@Repository
@Transactional
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String findById(String id) {
        Admin admin = em.find(Admin.class, id);
        return admin.getId();
    }

    /** id, pwd 일치하는 관리자 찾기 */
    public String findByIdPwd(String id, String pwd) {
        String s_pwd = jpaQueryFactory.select(admin.pwd)
                .from(admin)
                .where(JPAExpressions.selectFrom(admin)
                        .where(admin.id.eq(id)).exists()
                        .and(admin.id.eq(id)))
                .fetchOne();
        if(bCryptPasswordEncoder.matches(pwd, s_pwd)) return Grade.ADMIN.getStrType();
        return " ";
    }

    public Long userHasRow() {
        return (Long) em.createQuery("select count(u) from User u").getSingleResult()+(Long) em.createQuery("select count(t) from Teacher t").getSingleResult()+(Long) em.createQuery("select count(s) from Student s").getSingleResult();
    }

}
