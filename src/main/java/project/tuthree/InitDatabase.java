package project.tuthree;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.*;
import project.tuthree.domain.user.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDatabase {
    private final initService initService;

    @PostConstruct
    public void init() {
        log.info("\n---------------------\n" +
                "intiDatabase executing -- tuthree" +
                "\n---------------------");
        initService.init1(); //admin,postfaq, postnotice
        initService.init2(); //student
        initService.init3(); //parent
        initService.init4(); //teacher, post testpaper
        initService.init5();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class initService{

        private final EntityManager em;

        public void init1(){

            log.info("\n---------------------\n" +
                    "intiDatabase executing -- admin" +
                    "\n---------------------");

            Admin admin = new Admin("admin", "admin");
            Admin admin1 = new Admin("admin1", "admin1");
            Admin admin2 = new Admin("test2", "test2");
            em.persist(admin);
            em.persist(admin1);
            em.persist(admin2);


            PostFaq faq1 = new PostFaq(admin1, "title1", "content1",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY);
            PostFaq faq2 = new PostFaq(admin1, "title2", "content2",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY);
            PostFaq faq3 = new PostFaq(admin1, "title3", "content3",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY);
            PostFaq faq4 = new PostFaq(admin1, "title4", "content4",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY);
            PostFaq faq5 = new PostFaq(admin1, "title5", "content5",0L, new Date(), null, Status.OPEN, FaqType.CERTIFY);
            PostFaq faq6 = new PostFaq(admin1, "title6", "content6",0L, new Date(), null, Status.OPEN, FaqType.MANAGE);
            PostFaq faq7 = new PostFaq(admin1, "title7", "content7",0L, new Date(), null, Status.OPEN, FaqType.MANAGE);
            PostFaq faq8 = new PostFaq(admin1, "title8", "content8",0L, new Date(), null, Status.OPEN, FaqType.MANAGE);
            PostFaq faq9 = new PostFaq(admin1, "title9", "content9",0L, new Date(), null, Status.OPEN, FaqType.MANAGE);
            PostFaq faq10 = new PostFaq(admin1, "title10", "content10",0L, new Date(), null, Status.OPEN, FaqType.MATCHING);
            PostFaq faq11 = new PostFaq(admin1, "title11", "content11",0L, new Date(), null, Status.OPEN, FaqType.MATCHING);
            PostFaq faq12 = new PostFaq(admin1, "title12", "content12",0L, new Date(), null, Status.OPEN, FaqType.MATCHING);
            em.persist(faq1);
            em.persist(faq2);
            em.persist(faq3);
            em.persist(faq4);
            em.persist(faq5);
            em.persist(faq6);
            em.persist(faq7);
            em.persist(faq8);
            em.persist(faq9);
            em.persist(faq10);
            em.persist(faq11);
            em.persist(faq12);

            PostNotice notice1 = new PostNotice(admin1, "title1", "content1",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL);
            PostNotice notice2 = new PostNotice(admin1, "title2", "content2",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL);
            PostNotice notice3 = new PostNotice(admin1, "title3", "content3",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL);
            PostNotice notice4 = new PostNotice(admin1, "title4", "content4",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT);
            PostNotice notice5 = new PostNotice(admin1, "title5", "content5",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT);
            PostNotice notice6 = new PostNotice(admin1, "title6", "content6",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT);
            PostNotice notice7 = new PostNotice(admin1, "title7", "content7",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL);
            PostNotice notice8 = new PostNotice(admin1, "title8", "content8",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL);
            PostNotice notice9 = new PostNotice(admin1, "title9", "content9",0L, new Date(), null, Status.OPEN, NoticeType.NORMAL);
            PostNotice notice10 = new PostNotice(admin1, "title10", "content10",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT);
            PostNotice notice11 = new PostNotice(admin1, "title11", "content11",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT);
            PostNotice notice12 = new PostNotice(admin1, "title12", "content12",0L, new Date(), null, Status.OPEN, NoticeType.IMPORTANT);
            em.persist(notice1);
            em.persist(notice2);
            em.persist(notice3);
            em.persist(notice4);
            em.persist(notice5);
            em.persist(notice6);
            em.persist(notice4);
            em.persist(notice5);
            em.persist(notice6);
            em.persist(notice7);
            em.persist(notice8);
            em.persist(notice9);
            em.persist(notice10);        
            em.persist(notice11);        
            em.persist(notice12);
        }

        public void init2() {
            log.info("\n---------------------\n" +
                    "intiDatabase executing -- student" +
                    "\n---------------------");

            Student student1 = new Student("stu1", new BCryptPasswordEncoder().encode("stu1"), "name1", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture1", Status.CLOSE, Grade.STUDENT, new Date(), "s_region1", Status.OPEN, "subject1", 200000, School.H1, "잘부탁드립니다", null);
            Student student2 = new Student("stu2", new BCryptPasswordEncoder().encode("stu2"), "name2", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture2", Status.CLOSE, Grade.STUDENT, new Date(), "s_region2", Status.OPEN, "subject2", 200000, School.H2, "잘부탁드립니다", null);
            Student student3 = new Student("stu3", new BCryptPasswordEncoder().encode("stu3"), "name3", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture3", Status.OPEN, Grade.STUDENT, new Date(), "s_region3", Status.OPEN, "subject3", 200000, School.H3, "잘부탁드립니다", null);
            Student student4 = new Student("stu4", new BCryptPasswordEncoder().encode("stu4"), "name4", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture4", Status.OPEN, Grade.STUDENT, new Date(), "s_region4", Status.OPEN, "subject4", 200000, School.M1, "잘부탁드립니다", null);
            Student student5 = new Student("stu5", new BCryptPasswordEncoder().encode("stu5"), "name5", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture5", Status.OPEN, Grade.STUDENT, new Date(), "s_region5", Status.OPEN, "subject5", 200000, School.H2, "잘부탁드립니다", null);
            Student student6 = new Student("stu6", new BCryptPasswordEncoder().encode("stu6"), "name6", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture6", Status.OPEN, Grade.STUDENT, new Date(), "s_region6", Status.OPEN, "subject6", 200000, School.EXAM_M, "잘부탁드립니다", null);
            Student student7 = new Student("stu7", new BCryptPasswordEncoder().encode("stu7"), "name7", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture7", Status.OPEN, Grade.STUDENT, new Date(), "s_region7", Status.OPEN, "subject7", 200000, School.OVER_HIGH, "잘부탁드립니다", null);
            Student student8 = new Student("stu8", new BCryptPasswordEncoder().encode("stu8"), "name8", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture8", Status.CLOSE, Grade.STUDENT, new Date(), "s_region8", Status.OPEN, "subject8", 200000, School.M1, "잘부탁드립니다", null);
            Student student9 = new Student("stu9", new BCryptPasswordEncoder().encode("stu9"), "name9", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture9", Status.OPEN, Grade.STUDENT, new Date(), "s_region9", Status.OPEN, "subject9", 200000, School.M2, "잘부탁드립니다", null);
            Student student10 = new Student("stu10", new BCryptPasswordEncoder().encode("stu10"), "name10", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture10", Status.CLOSE, Grade.STUDENT, new Date(), "s_region10", Status.OPEN, "subject10", 200000, School.H1, "잘부탁드립니다", null);
            Student student11 = new Student("stu11", new BCryptPasswordEncoder().encode("stu11"), "name11", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture11", Status.CLOSE, Grade.STUDENT, new Date(), "s_region11", Status.OPEN, "subject11", 200000, School.H2, "잘부탁드립니다", null);
            Student student12 = new Student("stu12", new BCryptPasswordEncoder().encode("stu12"), "name12", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture12", Status.CLOSE, Grade.STUDENT, new Date(), "s_region12", Status.OPEN, "subject12", 200000, School.EXAM_M, "잘부탁드립니다", null);
            Student student13 = new Student("stu13", new BCryptPasswordEncoder().encode("stu13"), "name13", "email", "01012341234", Sex.FEMALE, 1990, "picture13", Status.OPEN, Grade.STUDENT, new Date(), "s_region13", Status.OPEN, "subject13", 200000, School.OVER_HIGH, "잘부탁드립니다", null);
            Student student14 = new Student("stu14", new BCryptPasswordEncoder().encode("stu14"), "name14", "email", "01012341234", Sex.FEMALE, 1990, "picture14", Status.OPEN, Grade.STUDENT, new Date(), "s_region14", Status.OPEN, "subject14", 200000, School.M1, "잘부탁드립니다", null);
            Student student15 = new Student("stu15", new BCryptPasswordEncoder().encode("stu15"), "name15", "email", "01012341234", Sex.FEMALE, 1990, "picture15", Status.OPEN, Grade.STUDENT, new Date(), "s_region15", Status.OPEN, "subject15", 200000, School.M2, "잘부탁드립니다", null);
            Student student16 = new Student("stu16", new BCryptPasswordEncoder().encode("stu16"), "name16", "email", "01012341234", Sex.FEMALE, 1990, "picture16", Status.OPEN, Grade.STUDENT, new Date(), "s_region16", Status.OPEN, "subject16", 200000, School.M3, "잘부탁드립니다", null);
            Student student17 = new Student("stu17", new BCryptPasswordEncoder().encode("stu17"), "name17", "email", "01012341234", Sex.FEMALE, 1990, "picture17", Status.OPEN, Grade.STUDENT, new Date(), "s_region17", Status.OPEN, "subject17", 200000, School.UNDER_MIDDLE, "잘부탁드립니다", null);
            Student student18 = new Student("stu18", new BCryptPasswordEncoder().encode("stu18"), "name18", "email", "01012341234", Sex.FEMALE, 1990, "picture18", Status.OPEN, Grade.STUDENT, new Date(), "s_region18", Status.OPEN, "subject18", 200000, School.H1, "잘부탁드립니다", null);
            Student student19 = new Student("stu19", new BCryptPasswordEncoder().encode("stu19"), "name19", "email", "01012341234", Sex.FEMALE, 1990, "picture19", Status.OPEN, Grade.STUDENT, new Date(), "s_region19", Status.OPEN, "subject19", 200000, School.H2, "잘부탁드립니다", null);
            Student student20 = new Student("stu20", new BCryptPasswordEncoder().encode("stu20"), "name20", "email", "01012341234", Sex.FEMALE, 1990, "picture20", Status.CLOSE, Grade.STUDENT, new Date(), "s_region20", Status.OPEN, "subject20", 200000, School.H3, "잘부탁드립니다", null);
            em.persist(student1);
            em.persist(student2);
            em.persist(student3);
            em.persist(student4);
            em.persist(student5);
            em.persist(student6);
            em.persist(student7);
            em.persist(student8);
            em.persist(student9);
            em.persist(student10);
            em.persist(student11);
            em.persist(student12);
            em.persist(student13);
            em.persist(student14);
            em.persist(student15);
            em.persist(student16);
            em.persist(student17);
            em.persist(student18);
            em.persist(student19);
            em.persist(student20);
        }

        public void init3() {
            log.info("\n---------------------\n" +
                    "intiDatabase executing -- parent" +
                    "\n---------------------");

            User user1 = new User("parent1", new BCryptPasswordEncoder().encode("parent1"), "name1", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture1", Status.CLOSE, Grade.PARENT, new Date());
            User user2 = new User("parent2", new BCryptPasswordEncoder().encode("parent2"), "name2", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture2", Status.CLOSE, Grade.PARENT, new Date());
            User user3 = new User("parent3", new BCryptPasswordEncoder().encode("parent3"), "name3", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture3", Status.CLOSE, Grade.PARENT, new Date());
            User user4 = new User("parent4", new BCryptPasswordEncoder().encode("parent4"), "name4", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture4", Status.CLOSE, Grade.PARENT, new Date());
            User user5 = new User("parent5", new BCryptPasswordEncoder().encode("parent5"), "name5", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture5", Status.CLOSE, Grade.PARENT, new Date());
            User user6 = new User("parent6", new BCryptPasswordEncoder().encode("parent6"), "name6", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture6", Status.CLOSE, Grade.PARENT, new Date());
            User user7 = new User("parent7", new BCryptPasswordEncoder().encode("parent7"), "name7", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture7", Status.CLOSE, Grade.PARENT, new Date());
            User user8 = new User("parent8", new BCryptPasswordEncoder().encode("parent8"), "name8", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture8", Status.CLOSE, Grade.PARENT, new Date());
            User user9 = new User("parent9", new BCryptPasswordEncoder().encode("parent9"), "name9", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture9", Status.CLOSE, Grade.PARENT, new Date());
            User user10 = new User("parent10", new BCryptPasswordEncoder().encode("parent10"), "name10", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture10", Status.OPEN, Grade.PARENT, new Date());
            User user11 = new User("parent11", new BCryptPasswordEncoder().encode("parent11"), "name11", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture11", Status.OPEN, Grade.PARENT, new Date());
            User user12 = new User("parent12", new BCryptPasswordEncoder().encode("parent12"), "name12", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture12", Status.OPEN, Grade.PARENT, new Date());
            User user13 = new User("parent13", new BCryptPasswordEncoder().encode("parent13"), "name13", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture13", Status.OPEN, Grade.PARENT, new Date());
            User user14 = new User("parent14", new BCryptPasswordEncoder().encode("parent14"), "name14", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture14", Status.OPEN, Grade.PARENT, new Date());
            User user15 = new User("parent15", new BCryptPasswordEncoder().encode("parent15"), "name15", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture15", Status.OPEN, Grade.PARENT, new Date());
            User user16 = new User("parent16", new BCryptPasswordEncoder().encode("parent16"), "name16", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture16", Status.OPEN, Grade.PARENT, new Date());
            User user17 = new User("parent17", new BCryptPasswordEncoder().encode("parent17"), "name17", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture17", Status.OPEN, Grade.PARENT, new Date());
            User user18 = new User("parent18", new BCryptPasswordEncoder().encode("parent18"), "name18", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture18", Status.OPEN, Grade.PARENT, new Date());
            User user19 = new User("parent19", new BCryptPasswordEncoder().encode("parent19"), "name19", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture19", Status.OPEN, Grade.PARENT, new Date());
            User user20 = new User("parent20", new BCryptPasswordEncoder().encode("parent20"), "name20", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture20", Status.OPEN, Grade.PARENT, new Date());
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(user4);
            em.persist(user5);
            em.persist(user6);
            em.persist(user7);
            em.persist(user8);
            em.persist(user9);
            em.persist(user10);
            em.persist(user11);
            em.persist(user12);
            em.persist(user13);
            em.persist(user14);
            em.persist(user15);
            em.persist(user16);
            em.persist(user17);
            em.persist(user18);
            em.persist(user19);
            em.persist(user20);
        }


        public void init4() {
            log.info("\n---------------------\n" +
                    "intiDatabase executing -- teacher" +
                    "\n---------------------");

            Teacher teacher2 = new Teacher("teacher2", new BCryptPasswordEncoder().encode("teacher2"), "name2", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture2", Status.OPEN, Grade.TEACHER, new Date(), "t_region2", Status.OPEN, "t_subject2", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif2", true, "잘 부탁드립니다");
            Teacher teacher1 = new Teacher("teacher1", new BCryptPasswordEncoder().encode("teacher1"), "name1", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture1", Status.OPEN, Grade.TEACHER, new Date(), "t_region1", Status.OPEN, "t_subject1", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif1", true, "잘 부탁드립니다");
            Teacher teacher3 = new Teacher("teacher3", new BCryptPasswordEncoder().encode("teacher3"), "name3", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture3", Status.OPEN, Grade.TEACHER, new Date(), "t_region3", Status.OPEN, "t_subject3", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif3", true, "잘 부탁드립니다");
            Teacher teacher4 = new Teacher("teacher4", new BCryptPasswordEncoder().encode("teacher4"), "name4", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture4", Status.OPEN, Grade.TEACHER, new Date(), "t_region4", Status.OPEN, "t_subject4", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif4", true, "잘 부탁드립니다");
            Teacher teacher5 = new Teacher("teacher5", new BCryptPasswordEncoder().encode("teacher5"), "name5", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture5", Status.OPEN, Grade.TEACHER, new Date(), "t_region5", Status.OPEN, "t_subject5", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif5", true, "잘 부탁드립니다");
            Teacher teacher6 = new Teacher("teacher6", new BCryptPasswordEncoder().encode("teacher6"), "name6", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture6", Status.OPEN, Grade.TEACHER, new Date(), "t_region6", Status.OPEN, "t_subject6", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif6", true, "잘 부탁드립니다");
            Teacher teacher7 = new Teacher("teacher7", new BCryptPasswordEncoder().encode("teacher7"), "name7", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture7", Status.OPEN, Grade.TEACHER, new Date(), "t_region7", Status.OPEN, "t_subject7", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif7", true, "잘 부탁드립니다");
            Teacher teacher8 = new Teacher("teacher8", new BCryptPasswordEncoder().encode("teacher8"), "name8", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture8", Status.OPEN, Grade.TEACHER, new Date(), "t_region8", Status.OPEN, "t_subject8", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif8", true, "잘 부탁드립니다");
            Teacher teacher9 = new Teacher("teacher9", new BCryptPasswordEncoder().encode("teacher9"), "name9", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture9", Status.OPEN, Grade.TEACHER, new Date(), "t_region9", Status.OPEN, "t_subject9", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif9", true, "잘 부탁드립니다");
            Teacher teacher10 = new Teacher("teacher10", new BCryptPasswordEncoder().encode("teacher10"), "name10", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture10", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject10", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", 0.0,"t_certif10", false, "잘 부탁드립니다");
            Teacher teacher11 = new Teacher("teacher11", new BCryptPasswordEncoder().encode("teacher11"), "name11", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture11", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject11", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", 0.0,"t_certif11", false, "잘 부탁드립니다");
            Teacher teacher12 = new Teacher("teacher12", new BCryptPasswordEncoder().encode("teacher12"), "name12", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture12", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject12", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", 0.0,"t_certif12", false, "잘 부탁드립니다");
            Teacher teacher13 = new Teacher("teacher13", new BCryptPasswordEncoder().encode("teacher13"), "name13", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture13", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject13", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", 0.0,"t_certif13", false, "잘 부탁드립니다");
            Teacher teacher14 = new Teacher("teacher14", new BCryptPasswordEncoder().encode("teacher14"), "name14", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture14", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject14", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", 0.0,"t_certif14", false, "잘 부탁드립니다");
            Teacher teacher15 = new Teacher("teacher15", new BCryptPasswordEncoder().encode("teacher15"), "name15", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture15", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject15", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", 0.0,"t_certif15", false, "잘 부탁드립니다");
            Teacher teacher16 = new Teacher("teacher16", new BCryptPasswordEncoder().encode("teacher16"), "name16", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture16", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject16", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", 0.0,"t_certif16", false, "잘 부탁드립니다");
            Teacher teacher17 = new Teacher("teacher17", new BCryptPasswordEncoder().encode("teacher17"), "name17", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture17", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject17", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", 0.0,"t_certif17", false, "잘 부탁드립니다");
            Teacher teacher18 = new Teacher("teacher18", new BCryptPasswordEncoder().encode("teacher18"), "name18", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture18", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject18", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", 0.0,"t_certif18", false, "잘 부탁드립니다");
            Teacher teacher19 = new Teacher("teacher19", new BCryptPasswordEncoder().encode("teacher19"), "name19", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture19", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject19", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", 0.0,"t_certif19", false, "잘 부탁드립니다");
            Teacher teacher20 = new Teacher("teacher20", new BCryptPasswordEncoder().encode("teacher20"), "name20", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture20", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject20", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", 0.0,"t_certif20", false, "잘 부탁드립니다");
            em.persist(teacher1);
            em.persist(teacher2);
            em.persist(teacher3);
            em.persist(teacher4);
            em.persist(teacher5);
            em.persist(teacher6);
            em.persist(teacher7);
            em.persist(teacher8);
            em.persist(teacher9);
            em.persist(teacher10);
            em.persist(teacher11);
            em.persist(teacher12);
            em.persist(teacher13);
            em.persist(teacher14);
            em.persist(teacher15);
            em.persist(teacher16);
            em.persist(teacher17);
            em.persist(teacher18);
            em.persist(teacher19);
            em.persist(teacher20);

            PostTestPaper postTestPaper1 = new PostTestPaper(teacher1, "title1 ", "content1 ", 0L, new Date(), new Date(), Status.CLOSE);
            PostTestPaper postTestPaper2 = new PostTestPaper(teacher2, "title2 ", "content2 ", 0L, new Date(), new Date(), Status.CLOSE);
            PostTestPaper postTestPaper3 = new PostTestPaper(teacher3, "title3 ", "content3 ", 0L, new Date(), new Date(), Status.CLOSE);
            PostTestPaper postTestPaper4 = new PostTestPaper(teacher4, "title4 ", "content4 ", 0L, new Date(), new Date(), Status.CLOSE);
            PostTestPaper postTestPaper5 = new PostTestPaper(teacher5, "title5 ", "content5 ", 0L, new Date(), new Date(), Status.CLOSE);
            PostTestPaper postTestPaper6 = new PostTestPaper(teacher6, "title6 ", "content6 ", 0L, new Date(), new Date(), Status.CLOSE);
            PostTestPaper postTestPaper7 = new PostTestPaper(teacher7, "title7 ", "content7 ", 0L, new Date(), new Date(), Status.CLOSE);
            PostTestPaper postTestPaper8 = new PostTestPaper(teacher8, "title8 ", "content8 ", 0L, new Date(), new Date(), Status.CLOSE);
            PostTestPaper postTestPaper9 = new PostTestPaper(teacher9, "title9 ", "content9 ", 0L, new Date(), new Date(), Status.CLOSE);
            PostTestPaper postTestPaper10 = new PostTestPaper(teacher10, "title10", "content10", 0L, new Date(), null, Status.OPEN);
            PostTestPaper postTestPaper11 = new PostTestPaper(teacher11, "title11", "content11", 0L, new Date(), null, Status.OPEN);
            PostTestPaper postTestPaper12 = new PostTestPaper(teacher12, "title12", "content12", 0L, new Date(), null, Status.OPEN);
            PostTestPaper postTestPaper13 = new PostTestPaper(teacher13, "title13", "content13", 0L, new Date(), null, Status.OPEN);
            PostTestPaper postTestPaper14 = new PostTestPaper(teacher14, "title14", "content14", 0L, new Date(), null, Status.OPEN);
            PostTestPaper postTestPaper15 = new PostTestPaper(teacher15, "title 15", "content15", 0L, new Date(), null, Status.OPEN);

            em.persist(postTestPaper1);
            em.persist(postTestPaper2);
            em.persist(postTestPaper3);
            em.persist(postTestPaper4);
            em.persist(postTestPaper5);
            em.persist(postTestPaper6);
            em.persist(postTestPaper7);
            em.persist(postTestPaper8);
            em.persist(postTestPaper9);
            em.persist(postTestPaper10);
            em.persist(postTestPaper11);
            em.persist(postTestPaper12);
            em.persist(postTestPaper13);
            em.persist(postTestPaper14);
            em.persist(postTestPaper15);

            PostFind post1 = new PostFind(teacher1, null, 0L, new Date(), null);
            PostFind post2 = new PostFind(teacher2, null, 0L, new Date(), null);
            PostFind post3 = new PostFind(teacher3, null, 0L, new Date(), null);
            PostFind post4 = new PostFind(teacher4, null, 0L, new Date(), null);
            PostFind post5 = new PostFind(teacher5, null, 0L, new Date(), null);
            PostFind post6 = new PostFind(teacher6, null, 0L, new Date(), null);
            PostFind post7 = new PostFind(teacher7, null, 0L, new Date(), null);
            PostFind post8 = new PostFind(teacher8, null, 0L, new Date(), null);
            PostFind post9 = new PostFind(teacher9, null, 0L, new Date(), null);
            PostFind post10 = new PostFind(teacher10, null, 0L, new Date(), null);
            PostFind post11 = new PostFind(teacher11, null, 0L, new Date(), null);
            PostFind post12 = new PostFind(teacher12, null, 0L, new Date(), null);
            PostFind post13 = new PostFind(teacher13, null, 0L, new Date(), null);
            PostFind post14 = new PostFind(teacher14, null, 0L, new Date(), null);
            PostFind post15 = new PostFind(teacher15, null, 0L, new Date(), null);
            PostFind post16 = new PostFind(teacher16, null, 0L, new Date(), null);
            PostFind post17 = new PostFind(teacher17, null, 0L, new Date(), null);
            PostFind post18 = new PostFind(teacher18, null, 0L, new Date(), null);
            PostFind post19 = new PostFind(teacher19, null, 0L, new Date(), null);

            em.persist(post1);
            em.persist(post2);
            em.persist(post3);
            em.persist(post4);
            em.persist(post5);
            em.persist(post6);
            em.persist(post7);
            em.persist(post8);
            em.persist(post9);
            em.persist(post10);
            em.persist(post11);
            em.persist(post12);
            em.persist(post13);
            em.persist(post14);
            em.persist(post15);
            em.persist(post16);
            em.persist(post17);
            em.persist(post18);
            em.persist(post19);
        }


        public void init5() {

        }
    }
}
