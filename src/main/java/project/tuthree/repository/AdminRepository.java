package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.Admin;

import javax.persistence.EntityManager;

@Repository
@Transactional
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;

    public String findById(String id) {
        Admin admin = em.find(Admin.class, id);
        return admin.getId();
    }
}
