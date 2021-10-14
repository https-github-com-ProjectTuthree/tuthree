package project.tuthree.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import project.tuthree.domain.user.*;

import javax.persistence.EntityManager;
import java.util.List;

import static project.tuthree.domain.user.QStudent.student;
import static project.tuthree.domain.user.QTeacher.teacher;
import static project.tuthree.domain.user.QUser.user;
import static project.tuthree.domain.user.QUserInfo.userInfo;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserEntityRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    /** user id로 찾기 */
    public Student studentFindById(String id) {
        return em.find(Student.class, id);
    }
    public User parentFindById(String id) {
        return em.find(User.class, id);
    }
    public Teacher teacherFindById(String id) {
        return em.find(Teacher.class, id);
    }

    /**
     * id와 pw 일치하는 사용자 찾기
     */
    public String studentFindByIdPwd(String id, String pwd) {
        if(studentRepository.existsById(id)){
            String s_pwd = jpaQueryFactory.select(student.pwd).from(student)
                    .where(student.id.eq(id)).fetchOne();
            if(bCryptPasswordEncoder.matches(pwd, s_pwd)) return Grade.STUDENT.getStrType();
            return " ";
        }
        return " ";
    }

    public String parentFindByIdPwd(String id, String pwd) {
        if(userRepository.existsById(id)){
            String s_pwd = jpaQueryFactory.select(user.pwd).from(user)
                    .where(user.id.eq(id)).fetchOne();
            if(bCryptPasswordEncoder.matches(pwd, s_pwd)) return Grade.PARENT.getStrType();
            return " ";
        }
        return " ";
    }

    public String teacherFindByIdPwd(String id, String pwd) {
        if(teacherRepository.existsById(id)){
            String s_pwd = jpaQueryFactory.select(teacher.pwd).from(teacher)
                    .where(teacher.id.eq(id)).fetchOne();
            if(bCryptPasswordEncoder.matches(pwd, s_pwd)) return Grade.TEACHER.getStrType();
            return " ";
        }
        return " ";
    }

    /** 지역 정보 저장 */
    public void userSaveRegion(String userId, List<String> region) {
        for (String r : region) {
            UserInfo userInfo = new UserInfo(userId, r, null);
            em.persist(userInfo);
        }
    }

    /** 지역 정보 찾기 */
    public List<String> userFindRegion(String id) {
        return jpaQueryFactory.select(userInfo.region)
                .from(userInfo)
                .where(userInfo.region.isNotNull()
                        .and(userInfo.userId.eq(id)))
                .fetch();
    }

    /** 과목 정보 저장 */
    public void userSaveSubject(String userId, List<String> subject) {
        for(String s : subject) {
            UserInfo userInfo = new UserInfo(userId, null, s);
            em.persist(userInfo);
        }
    }

    /** 과목 정보 찾기 */
    public List<String> userFindSubject(String id) {

        return jpaQueryFactory.select(userInfo.subject)
                .from(userInfo)
                .where(userInfo.subject.isNotNull()
                        .and(userInfo.userId.eq(id)))
                .fetch();
    }

}
