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

    Optional<Student> findByName(String name);

    Optional<Student> findByTel(String tel);

    List<Student> findAll();
}
