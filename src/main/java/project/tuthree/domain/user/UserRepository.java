package project.tuthree.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //@PersistenceContext
    //EnitityManger em;

    //아이디중복체크
    boolean existsById(String id);


    //로그인
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findById(String id);

    Optional<User> findByName(String name);

    Optional<User> findByTel(String tel);

    List<User> findAll();

}