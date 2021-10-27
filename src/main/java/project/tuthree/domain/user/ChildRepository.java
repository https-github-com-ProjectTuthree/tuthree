package project.tuthree.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    /**자녀요청찾기**/
    Child findByParentIdAndStudentId(String parentId, String studentId);

    /**자녀 요청 조회**/
    Child findByStudentId(String studentId);

    /**자녀 찾기**/
    Child findByParentIdAndStatusTrue(String parentId);
}
