package project.tuthree.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.PostFind;
import project.tuthree.domain.user.*;
import project.tuthree.dto.post.PostfindDTO;
import project.tuthree.mapper.PostFindMapper;
import project.tuthree.repository.PostFindRepository;
import project.tuthree.repository.UserEntityRepository;
import project.tuthree.repository.UserFileRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static project.tuthree.domain.post.QPostFind.postFind;
import static project.tuthree.domain.user.QStudent.student;
import static project.tuthree.domain.user.QTeacher.teacher;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostFindService {

    private final int setPage = 25;

    private final JPAQueryFactory jpaQueryFactory;
    private final PostFindRepository postFindRepository;
    private final UserFileRepository userFileRepository;
    private final PostFindMapper postFindMapper;
    private final UserEntityRepository userEntityRepository;

    /**
     * 정렬 쿼리, 목록 조회에 사용하기
     * 가격 : 낮은 순, 높은 순
     * 올린 시간 : 최신순
     *
     */
//    public JPAQuery findPostFindListSort() {
//
//    }

    /**
     * 선생님 postfind 목록 조회  - registration 상관x
     */
    public List<PostFindTeacherListDTO> findTeacherList(int page) throws IOException {
        List<PostFind> findList = jpaQueryFactory.selectFrom(postFind)
                .join(postFind.teacherId, teacher).orderBy(teacher.create_date.desc())
                .offset(setPage * (page - 1)).limit(setPage)
                .fetch();

        List<PostFindTeacherListDTO> list = new ArrayList<>();

        for (PostFind f : findList) {
            byte[] file = userFileRepository.transferUserFile(f.getTeacherId().getPost());

            PostFindTeacherListDTO dto = new PostFindTeacherListDTO(f.getId(), f.getTeacherId().getName(), f.getTeacherId().getSchool(),
                    f.getTeacherId().getMajor(), f.getTeacherId().getStar(), f.getTeacherId().getCost(), f.getTeacherId().getRegistration(), file);
            list.add(dto);

        }
        return list;
    }

    /**
     * 학생 postfind 목록 조회 - registration 상관 x
     */
    public List<PostFindStudentListDTO> findStudentList(int page) throws IOException {
        List<PostFind> findList = jpaQueryFactory.selectFrom(postFind)
                .join(postFind.studentId, student).orderBy(student.create_date.desc())
                .offset(setPage * (page - 1)).limit(setPage)
                .fetch();

        List<PostFindStudentListDTO> list = new ArrayList<>();

        for (PostFind f : findList) {
            byte[] file = userFileRepository.transferUserFile(f.getStudentId().getPost());
            PostFindStudentListDTO dto = new PostFindStudentListDTO(f.getId(), f.getStudentId().getName(),
                    f.getStudentId().getCost(), f.getStudentId().getRegistration(), file);
            list.add(dto);

        }
        return list;
    }

    /** 특정 선생님 조회 */
    public PostFindTeacherDTO findTeacher(Long postId) throws IOException {
        String userId = jpaQueryFactory.select(postFind.teacherId.id).from(postFind)
                .where(postFind.id.eq(postId)).fetchOne();
        Teacher teacher = userEntityRepository.teacherFindById(userId);
        byte[] file = userFileRepository.transferUserFile(teacher.getPost());

        return new PostFindTeacherDTO(postId, teacher.getName(), teacher.getSex(), teacher.getBirth(), teacher.getSchool(),
                teacher.getMajor(), teacher.getStatus(), teacher.getStar(), teacher.getCost(),
                teacher.getRegistration(), teacher.getDetail(), file);
    }

    /** 특정 학생 조회 */
    public PostFindStudentDTO findStudent(Long postId) throws IOException {
        String userId = jpaQueryFactory.select(postFind.studentId.id).from(postFind)
                .where(postFind.id.eq(postId)).fetchOne();
        Student student = userEntityRepository.studentFindById(userId);
        byte[] file = userFileRepository.transferUserFile(student.getPost());

        return new PostFindStudentDTO(postId, student.getName(), student.getSex(), student.getBirth(),
                student.getCost(), student.getSchool(), student.getRegistration(), student.getDetail(), file);
    }

    /** 과외 구하는 글 올리기 */
    public void teacherPostFindRegister(String userId) {
        Teacher teacher = userEntityRepository.teacherFindById(userId);
        PostfindDTO postfindDTO = new PostfindDTO(null, teacher, null, 0L, new Date(), null);
        postFindRepository.postRegister(postFindMapper.toEntity(postfindDTO));
    }

    public void studentPostFindRegister(String userId) {
        Student student = userEntityRepository.studentFindById(userId);
        PostfindDTO postfindDTO = new PostfindDTO(null, null, student, 0L, new Date(), null);
        postFindRepository.postRegister(postFindMapper.toEntity(postfindDTO));
    }


    @Getter
    @AllArgsConstructor
    public static class PostFindTeacherListDTO {
        /**
         * 이름 | 학교 | 과 | 과목 | 지역 | 가격
         * 모집중, 모집마감 | 별점 | 이미지
         */
        Long postId;
        String name;
        String school;
        String major;
        double star;
        Integer cost;
        Status registration;
        byte[] post;
    }

    @Getter
    @AllArgsConstructor
    public static class PostFindStudentListDTO {
        /**
         * 이름 | 과목 | 지역 | 가격
         * 모집중, 모집마감 | 이미지
         */
        Long postId;
        String name;
        Integer cost;
        Status registration;
        byte[] post;
    }

    @Getter
    @AllArgsConstructor
    public static class PostFindTeacherDTO {
        /**
         * 이름 | 학교 | 과 | 과목 | 지역 | 가격
         * 모집중, 모집마감 | 별점 | 이미지
         */
        Long postId;
        String name;
        Sex sex;
        int birth;
        String school;
        String major;
        SchoolStatus status;
        double star;
        Integer cost;
        Status registration;
        String detail;
        byte[] post;
    }

    @Getter
    @AllArgsConstructor
    public static class PostFindStudentDTO {
        /**
         * 이름 | 과목 | 지역 | 가격
         * 모집중, 모집마감 | 이미지
         */
        Long postId;
        String name;
        Sex sex;
        Integer birth;
        Integer cost;
        School school;
        Status registration;
        String detail;
        byte[] post;
    }


}
