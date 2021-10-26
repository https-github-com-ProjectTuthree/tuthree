package project.tuthree.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final UserInfoRepository userInfoRepository;

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
    public Map<String, String> studentFindByIdPwd(String id, String pwd) {
        Map<String, String> map = new HashMap<>();
        if(studentRepository.existsById(id)){
            Tuple tuple = jpaQueryFactory.select(student.name, student.pwd).from(student)
                    .where(student.id.eq(id)).fetchOne();
            String name = tuple.get(0, String.class);
            String sPwd = tuple.get(1, String.class);
            if(bCryptPasswordEncoder.matches(pwd, sPwd)) {
                map.put("name", name);
                map.put("grade", Grade.STUDENT.getStrType());
                return map;
            }
        }
        map.put("grade", " ");
        return map;
    }

    public Map<String, String> parentFindByIdPwd(String id, String pwd) {
        Map<String, String> map = new HashMap<>();
        if(userRepository.existsById(id)){
            Tuple tuple = jpaQueryFactory.select(user.name, user.pwd).from(user)
                    .where(user.id.eq(id)).fetchOne();
            String name = tuple.get(0, String.class);
            String sPwd = tuple.get(1, String.class);
            if(bCryptPasswordEncoder.matches(pwd, sPwd)) {
                map.put("name", name);
                map.put("grade", Grade.PARENT.getStrType());
                return map;
            }
        }
        map.put("grade", " ");
        return map;
    }

    public Map<String, String> teacherFindByIdPwd(String id, String pwd) {
        Map<String, String> map = new HashMap<>();
        if(teacherRepository.existsById(id)){
            Tuple tuple = jpaQueryFactory.select(teacher.name, teacher.pwd).from(teacher)
                    .where(teacher.id.eq(id)).fetchOne();
            String name = tuple.get(0, String.class);
            String sPwd = tuple.get(1, String.class);
            if(bCryptPasswordEncoder.matches(pwd, sPwd)) {
                map.put("name", name);
                map.put("grade", Grade.TEACHER.getStrType());
                return map;
            }
        }
        map.put("grade", " ");
        return map;
    }

    /** 지역 정보 저장 */
    public void userSaveRegion(String userId, List<String> region) {
        for (String r : region) {
            UserInfo userInfo = new UserInfo(userId, r, null);
            em.persist(userInfo);
        }
    }

    public void userUpdateRegion(String userId, List<String> region) {
        //userInfoRepository.deleteByUserId(userId);
        //userInfoRepository.flush();
        for (String r : region) {
            UserInfo userInfo = new UserInfo(userId, r, null);
            em.persist(userInfo);
        }
    }

    /** 지역 정보 찾기 */
    public List<String> userFindRegion(String id) {
        return jpaQueryFactory.select(userInfo.region).from(userInfo)
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

        return jpaQueryFactory.select(userInfo.subject).from(userInfo)
                .where(userInfo.subject.isNotNull()
                        .and(userInfo.userId.eq(id)))
                .fetch();
    }

    /** 자녀 아이디 목록 찾기 */
    public List<String> userFindChild(String id) {
        return jpaQueryFactory.select(student.id).from(student)
                .where(student.user.id.eq(id))
                .fetch();
    }


}
