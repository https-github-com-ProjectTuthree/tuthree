package project.tuthree.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {

    /**자녀요청찾기**/
    Child findByParentIdAndStudentId(String parentId, String studentId);

    Child findByStudentId(String studentId);
}