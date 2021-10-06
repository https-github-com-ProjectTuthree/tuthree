package project.tuthree.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.PostFind;
import project.tuthree.domain.user.QTeacher;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.post.PostfindDTO;
import project.tuthree.repository.PostFindRepository;

import java.util.List;
import java.util.stream.Collectors;

import static project.tuthree.domain.user.QTeacher.teacher;

@Service
@RequiredArgsConstructor
public class PostFindService {

    private final JPAQueryFactory jpaQueryFactory;
    private final PostFindRepository postFindRepository;

    class PostFindListDTO {
        /**
         * 이름
         * 학교
         * 과
         * 과목
         * 지역
         * 가격
         * 모집중, 모집마감
         * 별점
         * 이미지
         */
        Long postId;
        String name;
        String school;
        String major;
        List<String> subject;
        List<String> region;
        int cost;
        Status registration;
        int star; /////
        byte[] post;
    }

    /**
     * 선생님 postfind 목록 조회  - registration 상관x
     */
//    public List<PostFindListDTO> findTeacherList() {
//        List<String> teacherList = jpaQueryFactory.select(teacher.id)
//                .from(teacher).fetch();
//        List<PostFind> postFindList = teacherList.stream()
//                .map(m -> postFindRepository.findByUserTeacherId(m))
//                .collect(Collectors.toList());
//        return postFindList.stream()
//                .map(m -> new PostFindListDTO(m.getId(), m.getTeacherId().getName(), m.getTeacherId().getSchool()
//                ,m.getTeacherId().getMajor(), m.getTeacherId().getSubject(), m.getTeacherId().getRegion(), m.getTeacherId().getCost(),
//                        m.getTeacherId().getRegistration(), m.getTeacherId().star))
//    }
}
