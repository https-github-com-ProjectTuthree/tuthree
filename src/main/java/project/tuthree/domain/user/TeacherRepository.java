package project.tuthree.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    //아이디중복체크
    boolean existsById(String id);
    //로그인
    @EntityGraph(attributePaths = "authorities")
    Optional<Teacher> findById(String id);
}
