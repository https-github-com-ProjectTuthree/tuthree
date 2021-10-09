package project.tuthree.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    //아이디중복체크
    boolean existsById(String id);

    //로그인
    @EntityGraph(attributePaths = "authorities")
    Optional<Student> findById(String id);

    /** 이메일로 아이디 찾기**/
    Student findByNameAndEmail(String email, String name);

    /** 번호로 아이디 찾기**/
    Student findByNameAndTel(String tel, String name);

    /**이메일로 비밀번호**/
    String findByIdAndNameAndEmail(String id, String email, String name);

    /**번호로 비밀번호**/
    String findByIdAndNameAndTel(String id, String tel, String name);

    List<Student> findAll();
}
