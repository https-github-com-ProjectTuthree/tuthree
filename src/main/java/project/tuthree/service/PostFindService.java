package project.tuthree.service;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tuthree.domain.BookMark;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.PostFind;
import project.tuthree.domain.user.*;
import project.tuthree.dto.BookmarkDTO;
import project.tuthree.dto.post.PostfindDTO;
import project.tuthree.dto.user.StudentDTO;
import project.tuthree.dto.user.TeacherDTO;
import project.tuthree.mapper.BookMarkMapper;
import project.tuthree.mapper.PostFindMapper;
import project.tuthree.repository.PostFindRepository;
import project.tuthree.repository.PostFindRepository.PostFindSearchCondition;
import project.tuthree.repository.UserEntityRepository;
import project.tuthree.repository.UserFileRepository;
import project.tuthree.service.PostFindService.PostFindStudentCountListDTO.PostFindStudentListDTO;
import project.tuthree.service.PostFindService.PostFindTeacherCountListDTO.PostFindTeacherListDTO;

import static project.tuthree.exception.ExceptionSupplierImpl.wrap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostFindService {

    private final PostFindRepository postFindRepository;
    private final UserFileRepository userFileRepository;
    private final PostFindMapper postFindMapper;
    private final UserEntityRepository userEntityRepository;
    private final BookMarkMapper bookMarkMapper;
    private final AdminService adminService;

    /**
     * 선생님 postfind 목록 조회  - registration 상관x
     */
    public PostFindTeacherCountListDTO findTeacherList(int page, PostFindSearchCondition condition) throws IOException {
        //postfind
        JPAQuery<PostFind> query = postFindRepository.findTeacherQuery(condition);

        List<PostFind> teacher = postFindRepository.findTeacherFindList(page, query);
        Long teacherHasRow = postFindRepository.findTeacherHasRow(query);

        List<PostFindTeacherListDTO> list = new ArrayList<>();

        for (PostFind f : teacher) {
            /** 검색 후에 또 지역을 끌어옴... */
            List<String> region = userEntityRepository.userFindRegion(f.getTeacherId().getId());
            List<String> subject = userEntityRepository.userFindSubject(f.getTeacherId().getId());
            byte[] file = userFileRepository.transferUserFile(f.getTeacherId().getPost());

            PostFindTeacherListDTO dto = new PostFindTeacherListDTO(f.getId(), f.getTeacherId().getName(), f.getTeacherId().getSchool(),
                    f.getTeacherId().getMajor(), f.getTeacherId().getStar(), f.getTeacherId().getCost(), f.getTeacherId().getSex(),f.getTeacherId().getRegistration(),
                    region, subject, file);
            list.add(dto);
        }
        return new PostFindTeacherCountListDTO(teacherHasRow, list);
    }

    /**
     * 학생 postfind 목록 조회 - registration 상관 x
     */
    public PostFindStudentCountListDTO findStudentList(int page, PostFindSearchCondition condition) throws IOException {
        //postfind
        JPAQuery<PostFind> query = postFindRepository.findStudentQuery(condition);
        List<PostFind> student = postFindRepository.findStudentFindList(page, query);
        Long studentHasRow = postFindRepository.findStudentHasRow(query);
        List<PostFindStudentListDTO> list = new ArrayList<>();

        for (PostFind f : student) {

            /** 검색 후에 또 지역을 끌어옴... */
            List<String> region = userEntityRepository.userFindRegion(f.getStudentId().getId());
            List<String> subject = userEntityRepository.userFindSubject(f.getStudentId().getId());
            byte[] file = userFileRepository.transferUserFile(f.getStudentId().getPost());

            PostFindStudentListDTO dto = new PostFindStudentListDTO(f.getId(), f.getStudentId().getName(),
                    f.getStudentId().getCost(), f.getStudentId().getSex(), f.getStudentId().getRegistration(),
                    region, subject, file);
            list.add(dto);
        }
        return new PostFindStudentCountListDTO(studentHasRow, list);
    }

    /** 특정 선생님 조회 */
    public PostFindTeacherDTO findTeacher(Long postId) throws IOException {
        String userId = postFindRepository.findTeacherById(postId);
        Teacher teacher = userEntityRepository.teacherFindById(userId);

        List<String> region = userEntityRepository.userFindRegion(userId);
        List<String> subject = userEntityRepository.userFindSubject(userId);

        byte[] file = userFileRepository.transferUserFile(teacher.getPost());

        return new PostFindTeacherDTO(teacher.getId(), postId, teacher.getName(), teacher.getSex(), teacher.getBirth(), teacher.getSchool(),
                teacher.getMajor(), teacher.getStatus().getKortype(), teacher.getStar(), teacher.getCost(),
                teacher.getRegistration(), region, subject, teacher.getDetail(), file);
    }

    /** 특정 학생 조회 */
    public PostFindStudentDTO findStudent(Long postId) throws IOException {

        String userId = postFindRepository.findStudentById(postId);

        Student student = userEntityRepository.studentFindById(userId);
        List<String> region = userEntityRepository.userFindRegion(userId);

        List<String> subject = userEntityRepository.userFindSubject(userId);
        byte[] file = userFileRepository.transferUserFile(student.getPost());

        return new PostFindStudentDTO(student.getId(), postId, student.getName(), student.getSex(), student.getBirth(),
                student.getCost(), student.getSchool().getKorStatus(), student.getRegistration(), region, subject, student.getDetail(), file);
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

    /** 북마크 기능 */
    public Long addBookMark(String user1, String user2) {
        BookmarkDTO bookmarkDTO = new BookmarkDTO(user1, user2);
        return postFindRepository.addBookMark(bookMarkMapper.toEntity(bookmarkDTO));
    }

    /** 북마크 리스트 불러오기 - 학생이 선생님꺼 불러오기*/
    public List<BookMarkListDTO> studentListBookMark(String userId) {
        List<BookMark> bookMarks = postFindRepository.listBookMark(userId);
        List<BookMarkListDTO> dtoList = new ArrayList<>();
        bookMarks.stream().forEach(m -> wrap(() -> dtoList.add(new BookMarkListDTO(m.getId(), adminService.viewTeacher(m.getUser2()).updatePwdToNull()))));
        return dtoList;
    }

    /** 선생님이 학생을 불러오기 */
    public List<BookMarkListDTO> teacherListBookMark(String userId) {
        List<BookMark> bookMarks = postFindRepository.listBookMark(userId);
        List<BookMarkListDTO> dtoList = new ArrayList<>();
        bookMarks.stream().forEach(m -> wrap(() -> dtoList.add(new BookMarkListDTO(m.getId(), adminService.viewStudent(m.getUser2()).updatePwdToNull()))));
        return dtoList;

    }

    @Getter
    @AllArgsConstructor
    public static class PostFindTeacherCountListDTO {
        private Long count;
        private List<PostFindTeacherListDTO> list;

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
            String cost;
            Sex sex;
            Status registration;
            List<String> region;
            List<String> subject;
            byte[] post;
        }
    }


    @Getter
    @AllArgsConstructor
    public static class PostFindStudentCountListDTO {
        private Long count;
        private List<PostFindStudentListDTO> list;

        @Getter
        @AllArgsConstructor
        public static class PostFindStudentListDTO {
            /**
             * 이름 | 과목 | 지역 | 가격
             * 모집중, 모집마감 | 이미지
             */
            Long postId;
            String name;
            String cost;
            Sex sex;
            Status registration;
            List<String> region;
            List<String> subject;
            byte[] post;
        }
    }


    @Getter
    @AllArgsConstructor
    public static class PostFindTeacherDTO {
        /**
         * 이름 | 학교 | 과 | 과목 | 지역 | 가격
         * 모집중, 모집마감 | 별점 | 이미지
         */
        String userId;
        Long postId;
        String name;
        Sex sex;
        int birth;
        String school;
        String major;
        String status;
        double star;
        String cost;
        Status registration;
        List<String> region;
        List<String> subject;
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
        String userId;
        Long postId;
        String name;
        Sex sex;
        Integer birth;
        String cost;
        String school;
        Status registration;
        List<String> region;
        List<String> subject;
        String detail;
        byte[] post;
    }

    @Getter
    @AllArgsConstructor
    public static class BookMarkListDTO {
        private Long bookmarkId;
        private Object object;
    }

}
