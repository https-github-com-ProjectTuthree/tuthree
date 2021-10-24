package project.tuthree.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.tuthree.dto.user.UserListDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //아이디중복체크
    boolean existsById(String id);

    /** 이메일로 아이디 찾기**/
    User findByNameAndEmail(String email, String name);

    /** 번호로 아이디 찾기**/
    User findByNameAndTel(String tel, String name);

    /**이메일로 비밀번호**/
    User findByIdAndNameAndEmail(String id, String email, String name);

    /**번호로 비밀번호**/
    User findByIdAndNameAndTel(String id, String tel, String name);

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Page<User> findById(String id, Pageable pageable);

}