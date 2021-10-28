package project.tuthree.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.BookMark;
import project.tuthree.domain.QBookMark;
import project.tuthree.domain.post.PostFind;
import project.tuthree.domain.post.QPostFind;
import project.tuthree.domain.user.*;
import project.tuthree.service.PostFindService;
import project.tuthree.service.PostFindService.PostFindStudentCountListDTO.PostFindStudentListDTO;
import project.tuthree.service.PostFindService.PostFindTeacherCountListDTO.PostFindTeacherListDTO;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jsonwebtoken.lang.Strings.hasText;
import static project.tuthree.domain.QBookMark.bookMark;
import static project.tuthree.domain.post.QPostFind.postFind;
import static project.tuthree.domain.user.QStudent.student;
import static project.tuthree.domain.user.QTeacher.teacher;
import static project.tuthree.domain.user.QUserInfo.userInfo;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class PostFindRepository {

    private final int setPage = 12;

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final UserEntityRepository userEntityRepository;

    @Getter
    @AllArgsConstructor
    public static class PostFindSearchCondition {
        //정렬, 검색
        List<String> region;
        List<String> subject;
        String start;
        String end;
        String sort; //최신순, 오래된순, 가격 높은 순, 가격 낮은 순 (latest, old, high, low), 별점 높은 순, 별점 낮은 순
    }

    /** 선생님 post find 목록 찾기 */
    public JPAQuery<PostFind> findTeacherQuery(PostFindSearchCondition condition) {
        QUserInfo m = userInfo;
        QUserInfo n = new QUserInfo("n");
        BooleanBuilder builder = new BooleanBuilder();
        if(condition.getRegion() != null && !condition.getRegion().isEmpty()){
            List<Predicate> list = new ArrayList<>();
            condition.getRegion().stream().forEach(t -> list.add(n.region.contains(t)));
            builder.and(ExpressionUtils.anyOf(list));
        }

        if(condition.getSubject() != null && !condition.getSubject().isEmpty()){
            List<Predicate> list = new ArrayList<>();
            condition.getSubject().stream().forEach(t -> list.add(m.subject.contains(t)));
            builder.and(ExpressionUtils.anyOf(list));
        }
        if(hasText(condition.getStart())){
            builder.and(postFind.teacherId.cost.between(condition.getStart(), condition.getEnd()));
        }
        JPQLQuery<Teacher> where = JPAExpressions.select(postFind.teacherId).from(postFind, m, n).distinct()
                .where(postFind.teacherId.id.eq(userInfo.userId)
                        .and(m.userId.eq(n.userId)).and(builder));

        JPAQuery<PostFind> query = jpaQueryFactory.selectFrom(postFind)
                .where(postFind.teacherId.in(where));

        //정렬
        if(condition.getSort() != null && !condition.getSort().isEmpty()){
            switch (condition.getSort()) {
                case "latest" :
                    query.orderBy(postFind.teacherId.createDate.desc());
                    break;
                case "old" :
                    query.orderBy(postFind.teacherId.createDate.asc());
                    break;
                case "hprice" :
                    query.orderBy(postFind.teacherId.cost.desc());
                    break;
                case "lprice" :
                    query.orderBy(postFind.teacherId.cost.asc());
                    break;
                case "hstar" :
                    query.orderBy(postFind.teacherId.star.desc());
                    break;
                case "lstar" :
                    query.orderBy(postFind.teacherId.star.asc());
                    break;
                default:
                    break;
            }
        }
        return query;
    }
    public List<PostFind> findTeacherFindList(int page, JPAQuery<PostFind> query){
        return query.offset(setPage * (page - 1))
                .limit(setPage).fetch();
    }

    public Long findTeacherHasRow(JPAQuery<PostFind> query) {
        return query.fetchCount();
    }

    /** 학생 post find 목록 찾기 */

    public JPAQuery<PostFind> findStudentQuery(PostFindSearchCondition condition) {
        QUserInfo m = userInfo;
        QUserInfo n = new QUserInfo("n");
        BooleanBuilder builder = new BooleanBuilder();

        if(condition.getRegion() != null && !condition.getRegion().isEmpty()){
            List<Predicate> list = new ArrayList<>();
            condition.getRegion().stream().forEach(t -> list.add(n.region.contains(t)));
            builder.and(ExpressionUtils.anyOf(list));
        }
        if(condition.getSubject() != null && !condition.getSubject().isEmpty()){
            List<Predicate> list = new ArrayList<>();
            condition.getSubject().stream().forEach(t -> list.add(m.subject.contains(t)));
            builder.and(ExpressionUtils.anyOf(list));
        }
        if(hasText(condition.getStart())){
            builder.and(postFind.studentId.cost.between(condition.getStart(), condition.getEnd()));
        }

        JPQLQuery<Student> where = JPAExpressions.select(postFind.studentId).from(postFind, m, n).distinct()
                .where(postFind.studentId.id.eq(userInfo.userId)
                        .and(m.userId.eq(n.userId)).and(builder));

        JPAQuery<PostFind> query = jpaQueryFactory.selectFrom(postFind)
                .where(postFind.studentId.in(where));

        //정렬
        if(condition.getSort() != null && !condition.getSort().isEmpty()){
            switch (condition.getSort()) {
                case "latest" :
                    query.orderBy(postFind.studentId.createDate.desc());
                    break;
                case "old" :
                    query.orderBy(postFind.studentId.createDate.asc());
                    break;
                case "hprice" :
                    query.orderBy(postFind.studentId.cost.desc());
                    break;
                case "lprice" :
                    query.orderBy(postFind.studentId.cost.asc());
                    break;
                default:
                    break;
            }
        }
        return query;
    }
    public List<PostFind> findStudentFindList(int page, JPAQuery<PostFind> query){
        return query.offset(setPage * (page - 1))
                .limit(setPage).fetch();
    }

    public Long findStudentHasRow(JPAQuery<PostFind> query) {
        return query.fetchCount();
    }

    /** id로 특정 게시글 조회 */
    public PostFind findById(Long postId) {
        return em.find(PostFind.class, postId);
    }

    /** post id로 선생님 id 찾기 */
    public String findTeacherById(Long postId) {
        String teacherId = jpaQueryFactory.select(postFind.teacherId.id).from(postFind)
                .where(postFind.id.eq(postId)).fetchOne();
        return teacherId;
    }

    /** 선생님 id로 post find 찾기 */
    public List<PostFindTeacherListDTO> findTeacherByuserId(List<String> userId) {

        List<PostFindTeacherListDTO> list = new ArrayList<>();

        for(String s : userId) {
            PostFind p = jpaQueryFactory.selectFrom(QPostFind.postFind)
                    .where(QPostFind.postFind.teacherId.id.eq(s))
                    .fetchOne();
            Teacher t = p.getTeacherId();
            List<String> region = userEntityRepository.userFindRegion(s);
            List<String> subject = userEntityRepository.userFindSubject(s);
            PostFindTeacherListDTO dto = new PostFindTeacherListDTO(p.getId(), t.getName(), t.getSchool(), t.getMajor(), t.getStar(), t.getCost(),
                    t.getSex(), t.getRegistration(), region, subject, null);
            list.add(dto);
        }
        return list;

    }

    /** post id로 학생 id 찾기 */
    public String findStudentById(Long postId) {
        String userId = jpaQueryFactory.select(postFind.studentId.id).from(postFind)
                .where(postFind.id.eq(postId)).fetchOne();
        return userId;
    }

    /** 학생 id로 post find 찾기 */
    public List<PostFindStudentListDTO> findStudentByuserId(List<String> userId) {
        List<PostFindStudentListDTO> list = new ArrayList<>();

        for(String s : userId) {
            PostFind p = jpaQueryFactory.selectFrom(QPostFind.postFind)
                    .where(postFind.studentId.id.eq(s))
                    .fetchOne();
            Student t = p.getStudentId();
            List<String> region = userEntityRepository.userFindRegion(s);
            List<String> subject = userEntityRepository.userFindSubject(s);
            PostFindStudentListDTO dto = new PostFindStudentListDTO(p.getId(), t.getName(), t.getCost(), t.getSex(),
                   t.getRegistration(), region, subject, null);
            list.add(dto);
        }
        return list;
    }

    /** 선생님 게시글 갯수 */
    public Long teacherHasRow() {
        return jpaQueryFactory.selectFrom(postFind)
                .where(postFind.teacherId.isNotNull()).fetchCount();
    }

    /** 학생 게시글 갯수 */
    public Long studentHasRow() {
        return jpaQueryFactory.selectFrom(postFind)
                .where(postFind.studentId.isNotNull()).fetchCount();
    }

    /**
     * 과외 구하는 글 올리기
     * 학생 : 회원가입 시, 글 올림
     * 선생 : 증명서 확인 완료 시, 글 올림
     */
    public void postRegister(PostFind postFind) {
        em.persist(postFind);
    }

    /** 북마크 기능 */
    public Long addBookMark(BookMark bookMark) {
        em.persist(bookMark);
        return bookMark.getId();
    }

    /** 북마크 삭제 */
    public Long deleteBookMark(Long id) {
        em.remove(em.find(BookMark.class, id));
        return id;
    }

    /** 북마크 리스트 불러오기 */
    public List<String> listBookMark(String userId) {
        return jpaQueryFactory.select(bookMark.user2)
                .from(bookMark)
                .where(bookMark.user1.eq(userId))
                .fetch();
    }
}
