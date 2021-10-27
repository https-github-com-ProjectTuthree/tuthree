package project.tuthree.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    /**자녀요청찾기**/
    Child findByParentIdAndStudentId(String parentId, String studentId);

    /**자녀 요청 조회**/
    List<Child> findByStudentId(String studentId);

    /**자녀 찾기**/
    List<Child> findByParentIdAndStatusTrue(String parentId);
}
