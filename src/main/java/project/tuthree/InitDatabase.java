package project.tuthree;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitDatabase {
    private final initService initService;

    @PostConstruct
    public void init() {
        initService.init1();
    }
    @Component
    @RequiredArgsConstructor
    @Transactional
    static class initService{

        private final EntityManager em;

        public void init1(){
            Admin admin = new Admin("admin1234", "admin1234!");
            em.persist(admin);
            System.out.println(admin.getId());

            //user가 admin 것도 같이 수정하지를 못함..
            User parent = new User("user_id","pwd1","user1", new Mail("parent","domain.com"), new Tel("010","0000","0000"),
                    Sex.FEMALE, 2001,"/home/ubuntu/tell", Grade.PARENT, LocalDateTime.now());
            System.out.println(parent.getId());

            User parent1 = User.builder()
                    .id("parent1")
                    .pwd("pwd33")
                    .name("mimi")
                    .mail(new Mail("parent", "naver.com"))
                    .tel(new Tel("010", "0000", "0000"))
                    .sex(Sex.FEMALE)
                    .birth(2001)
                    .post("/picture/mimi")
                    .grade(Grade.PARENT)
                    .create_date(LocalDateTime.now())
                    .build();

        }

    }

}
