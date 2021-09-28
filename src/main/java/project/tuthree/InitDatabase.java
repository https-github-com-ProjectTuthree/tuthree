package project.tuthree;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.FaqType;
import project.tuthree.domain.post.NoticeType;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.domain.user.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
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

            Admin admin1 = new Admin("admin1", "admin1");em.persist(admin1);
            Admin admin2 = new Admin("test2", "test2"); em.persist(admin2);
            PostFaq faq1 = new PostFaq(admin1, "title1", "content1",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY); em.persist(faq1);
            PostFaq faq2 = new PostFaq(admin1, "title2", "content2",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY); em.persist(faq2);
            PostFaq faq3 = new PostFaq(admin1, "title3", "content3",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY); em.persist(faq3);
            PostFaq faq4 = new PostFaq(admin1, "title4", "content4",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY); em.persist(faq4);
            PostFaq faq5 = new PostFaq(admin1, "title5", "content5",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY); em.persist(faq5);
            PostFaq faq6 = new PostFaq(admin1, "title6", "content6",0L, new Date(), null, Status.OPEN, FaqType.MANAGE); em.persist(faq6);
            PostFaq faq7 = new PostFaq(admin1, "title7", "content7",0L, new Date(), null, Status.OPEN, FaqType.MANAGE); em.persist(faq7);
            PostFaq faq8 = new PostFaq(admin1, "title8", "content8",0L, new Date(), null, Status.OPEN, FaqType.MANAGE); em.persist(faq8);
            PostFaq faq9 = new PostFaq(admin1, "title9", "content9",0L, new Date(), null, Status.OPEN, FaqType.MANAGE); em.persist(faq9);
            PostFaq faq10 = new PostFaq(admin1, "title10", "content10",0L, new Date(), null, Status.OPEN, FaqType.MATCHING); em.persist(faq10);
            PostFaq faq11 = new PostFaq(admin1, "title11", "content11",0L, new Date(), null, Status.OPEN, FaqType.MATCHING); em.persist(faq11);
            PostFaq faq12 = new PostFaq(admin1, "title12", "content12",0L, new Date(), null, Status.OPEN, FaqType.MATCHING); em.persist(faq12);

            PostNotice notice1 = new PostNotice(admin1, "title1", "content1",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL); em.persist(notice1);
            PostNotice notice2 = new PostNotice(admin1, "title2", "content2",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL); em.persist(notice2);
            PostNotice notice3 = new PostNotice(admin1, "title3", "content3",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL); em.persist(notice3);
            PostNotice notice4 = new PostNotice(admin1, "title4", "content4",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT); em.persist(notice4);
            PostNotice notice5 = new PostNotice(admin1, "title5", "content5",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT); em.persist(notice5);
            PostNotice notice6 = new PostNotice(admin1, "title6", "content6",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT); em.persist(notice6);
            PostNotice notice7 = new PostNotice(admin1, "title7", "content7",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL); em.persist(notice7);
            PostNotice notice8 = new PostNotice(admin1, "title8", "content8",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL); em.persist(notice8);
            PostNotice notice9 = new PostNotice(admin1, "title9", "content9",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL); em.persist(notice9);
            PostNotice notice10 = new PostNotice(admin1, "title10", "content10",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT); em.persist(notice10);
            PostNotice notice11 = new PostNotice(admin1, "title11", "content11",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT); em.persist(notice11);
            PostNotice notice12 = new PostNotice(admin1, "title12", "content12",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT); em.persist(notice12);


//            Admin admin = new Admin("admin1234", "admin1234!");
//            em.persist(admin);
//            System.out.println(admin.getId());
//
//            //user가 admin 것도 같이 수정하지를 못함..
//            User parent = new User("user_id","pwd1","user1", new Mail("parent","domain.com"), new Tel("010","0000","0000"),
//                    Sex.FEMALE, 2001,"/home/ubuntu/tell", Grade.PARENT, new Date());
//            System.out.println(parent.getId());
//
//            User parent1 = User.builder()
//                    .id("parent1")
//                    .pwd("pwd33")
//                    .name("mimi")
//                    .mail(new Mail("parent", "naver.com"))
//                    .tel(new Tel("010", "0000", "0000"))
//                    .sex(Sex.FEMALE)
//                    .birth(2001)
//                    .post("/picture/mimi")
//                    .grade(Grade.PARENT)
//                    .create_date(new Date())
//                    .build();



        }

    }

}
