package project.tuthree.init;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import project.tuthree.domain.user.Admin;

import javax.persistence.EntityManager;

@Rollback
public class InitTest {

    @Autowired
    private EntityManager em;

    @Test
    public void initTest() {
        Admin admin = new Admin("admin1234", "admin1234!");
        System.out.println(admin.getId());

    }




}
