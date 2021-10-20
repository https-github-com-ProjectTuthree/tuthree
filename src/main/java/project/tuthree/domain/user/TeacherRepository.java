package project.tuthree.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    //아이디중복체크
    boolean existsById(String id);

    /** 이메일로 아이디 찾기**/
    Teacher findByNameAndEmail(String email, String name);

    /** 번호로 아이디 찾기**/
    Teacher findByNameAndTel(String tel, String name);

    /**이메일로 비밀번호**/
    Teacher findByIdAndNameAndEmail(String id, String email, String name);

    /**번호로 비밀번호**/
    Teacher findByIdAndNameAndTel(String id, String tel, String name);

    List<Teacher> findAll();

    Page<Teacher> findAll(Pageable pageable);
}
