package project.tuthree;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
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
//        initService.init1();
//        initService.init2();
//        initService.init3();
//        initService.init4();
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

            Admin admin = new Admin("admin", "admin");em.persist(admin);
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
        }

        public void init2() {
            log.info("\n---------------------\n" +
                    "intiDatabase executing -- student" +
                    "\n---------------------");

            Student student1 = new Student("stu1", "stu1", "name1", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture1", Status.CLOSE, Grade.STUDENT, new Date(), "s_region1", Status.OPEN, "subject1", 200000, School.H1, "잘부탁드립니다", null); em.persist(student1);
            Student student2 = new Student("stu2", "stu2", "name2", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture2", Status.CLOSE, Grade.STUDENT, new Date(), "s_region2", Status.OPEN, "subject2", 200000, School.H2, "잘부탁드립니다", null); em.persist(student2);
            Student student3 = new Student("stu3", "stu3", "name3", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture3", Status.OPEN, Grade.STUDENT, new Date(), "s_region3", Status.OPEN, "subject3", 200000, School.H3, "잘부탁드립니다", null); em.persist(student3);
            Student student4 = new Student("stu4", "stu4", "name4", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture4", Status.OPEN, Grade.STUDENT, new Date(), "s_region4", Status.OPEN, "subject4", 200000, School.M1, "잘부탁드립니다", null); em.persist(student4);
            Student student5 = new Student("stu5", "stu5", "name5", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture5", Status.OPEN, Grade.STUDENT, new Date(), "s_region5", Status.OPEN, "subject5", 200000, School.H2, "잘부탁드립니다", null); em.persist(student5);
            Student student6 = new Student("stu6", "stu6", "name6", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture6", Status.OPEN, Grade.STUDENT, new Date(), "s_region6", Status.OPEN, "subject6", 200000, School.EXAM_M, "잘부탁드립니다", null); em.persist(student6);
            Student student7 = new Student("stu7", "stu7", "name7", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture7", Status.OPEN, Grade.STUDENT, new Date(), "s_region7", Status.OPEN, "subject7", 200000, School.OVER_HIGH, "잘부탁드립니다", null); em.persist(student7);
            Student student8 = new Student("stu8", "stu8", "name8", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture8", Status.CLOSE, Grade.STUDENT, new Date(), "s_region8", Status.OPEN, "subject8", 200000, School.M1, "잘부탁드립니다", null); em.persist(student8);
            Student student9 = new Student("stu9", "stu9", "name9", "email@naver.com", "01012341234", Sex.MALE, 1990, "picture9", Status.OPEN, Grade.STUDENT, new Date(), "s_region9", Status.OPEN, "subject9", 200000, School.M2, "잘부탁드립니다", null); em.persist(student9);
            Student student10 = new Student("stu10", "stu10", "name10", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture10", Status.CLOSE, Grade.STUDENT, new Date(), "s_region10", Status.OPEN, "subject10", 200000, School.H1, "잘부탁드립니다", null); em.persist(student10);
            Student student11 = new Student("stu11", "stu11", "name11", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture11", Status.CLOSE, Grade.STUDENT, new Date(), "s_region11", Status.OPEN, "subject11", 200000, School.H2, "잘부탁드립니다", null); em.persist(student11);
            Student student12 = new Student("stu12", "stu12", "name12", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture12", Status.CLOSE, Grade.STUDENT, new Date(), "s_region12", Status.OPEN, "subject12", 200000, School.EXAM_M, "잘부탁드립니다", null); em.persist(student12);
            Student student13 = new Student("stu13", "stu13", "name13", "email", "01012341234", Sex.FEMALE, 1990, "picture13", Status.OPEN, Grade.STUDENT, new Date(), "s_region13", Status.OPEN, "subject13", 200000, School.OVER_HIGH, "잘부탁드립니다", null); em.persist(student13);
            Student student14 = new Student("stu14", "stu14", "name14", "email", "01012341234", Sex.FEMALE, 1990, "picture14", Status.OPEN, Grade.STUDENT, new Date(), "s_region14", Status.OPEN, "subject14", 200000, School.M1, "잘부탁드립니다", null); em.persist(student14);
            Student student15 = new Student("stu15", "stu15", "name15", "email", "01012341234", Sex.FEMALE, 1990, "picture15", Status.OPEN, Grade.STUDENT, new Date(), "s_region15", Status.OPEN, "subject15", 200000, School.M2, "잘부탁드립니다", null); em.persist(student15);
            Student student16 = new Student("stu16", "stu16", "name16", "email", "01012341234", Sex.FEMALE, 1990, "picture16", Status.OPEN, Grade.STUDENT, new Date(), "s_region16", Status.OPEN, "subject16", 200000, School.M3, "잘부탁드립니다", null); em.persist(student16);
            Student student17 = new Student("stu17", "stu17", "name17", "email", "01012341234", Sex.FEMALE, 1990, "picture17", Status.OPEN, Grade.STUDENT, new Date(), "s_region17", Status.OPEN, "subject17", 200000, School.UNDER_MIDDLE, "잘부탁드립니다", null); em.persist(student17);
            Student student18 = new Student("stu18", "stu18", "name18", "email", "01012341234", Sex.FEMALE, 1990, "picture18", Status.OPEN, Grade.STUDENT, new Date(), "s_region18", Status.OPEN, "subject18", 200000, School.H1, "잘부탁드립니다", null); em.persist(student18);
            Student student19 = new Student("stu19", "stu19", "name19", "email", "01012341234", Sex.FEMALE, 1990, "picture19", Status.OPEN, Grade.STUDENT, new Date(), "s_region19", Status.OPEN, "subject19", 200000, School.H2, "잘부탁드립니다", null); em.persist(student19);
            Student student20 = new Student("stu20", "stu20", "name20", "email", "01012341234", Sex.FEMALE, 1990, "picture20", Status.CLOSE, Grade.STUDENT, new Date(), "s_region20", Status.OPEN, "subject20", 200000, School.H3, "잘부탁드립니다", null); em.persist(student20);
        }

        public void init3() {
            log.info("\n---------------------\n" +
                    "intiDatabase executing -- parent" +
                    "\n---------------------");

            User user1 = new User("parent1", "parent1", "name1", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture1", Status.CLOSE, Grade.PARENT, new Date()); em.persist(user1);
            User user2 = new User("parent2", "parent2", "name2", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture2", Status.CLOSE, Grade.PARENT, new Date()); em.persist(user2);
            User user3 = new User("parent3", "parent3", "name3", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture3", Status.CLOSE, Grade.PARENT, new Date()); em.persist(user3);
            User user4 = new User("parent4", "parent4", "name4", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture4", Status.CLOSE, Grade.PARENT, new Date()); em.persist(user4);
            User user5 = new User("parent5", "parent5", "name5", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture5", Status.CLOSE, Grade.PARENT, new Date()); em.persist(user5);
            User user6 = new User("parent6", "parent6", "name6", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture6", Status.CLOSE, Grade.PARENT, new Date()); em.persist(user6);
            User user7 = new User("parent7", "parent7", "name7", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture7", Status.CLOSE, Grade.PARENT, new Date()); em.persist(user7);
            User user8 = new User("parent8", "parent8", "name8", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture8", Status.CLOSE, Grade.PARENT, new Date()); em.persist(user8);
            User user9 = new User("parent9", "parent9", "name9", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture9", Status.CLOSE, Grade.PARENT, new Date()); em.persist(user9);
            User user10 = new User("parent10", "parent10", "name10", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture10", Status.OPEN, Grade.PARENT, new Date()); em.persist(user10);
            User user11 = new User("parent11", "parent11", "name11", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture11", Status.OPEN, Grade.PARENT, new Date()); em.persist(user11);
            User user12 = new User("parent12", "parent12", "name12", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture12", Status.OPEN, Grade.PARENT, new Date()); em.persist(user12);
            User user13 = new User("parent13", "parent13", "name13", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture13", Status.OPEN, Grade.PARENT, new Date()); em.persist(user13);
            User user14 = new User("parent14", "parent14", "name14", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture14", Status.OPEN, Grade.PARENT, new Date()); em.persist(user14);
            User user15 = new User("parent15", "parent15", "name15", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture15", Status.OPEN, Grade.PARENT, new Date()); em.persist(user15);
            User user16 = new User("parent16", "parent16", "name16", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture16", Status.OPEN, Grade.PARENT, new Date()); em.persist(user16);
            User user17 = new User("parent17", "parent17", "name17", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture17", Status.OPEN, Grade.PARENT, new Date()); em.persist(user17);
            User user18 = new User("parent18", "parent18", "name18", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture18", Status.OPEN, Grade.PARENT, new Date()); em.persist(user18);
            User user19 = new User("parent19", "parent19", "name19", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture19", Status.OPEN, Grade.PARENT, new Date()); em.persist(user19);
            User user20 = new User("parent20", "parent20", "name20", "email@naver.com", "01012341234", Sex.MALE, 1990, "p_picture20", Status.OPEN, Grade.PARENT, new Date()); em.persist(user20);
        }

        public void init4() {
            log.info("\n---------------------\n" +
                    "intiDatabase executing -- teacher" +
                    "\n---------------------");

            Teacher teacher1 = new Teacher("teacher1", "teacher1", "name1", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture1", Status.OPEN, Grade.TEACHER, new Date(), "t_region1", Status.OPEN, "t_subject1", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", "t_certif1", true, "잘 부탁드립니다"); em.persist(teacher1);
            Teacher teacher2 = new Teacher("teacher2", "teacher2", "name2", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture2", Status.OPEN, Grade.TEACHER, new Date(), "t_region2", Status.OPEN, "t_subject2", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", "t_certif2", true, "잘 부탁드립니다"); em.persist(teacher2);
            Teacher teacher3 = new Teacher("teacher3", "teacher3", "name3", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture3", Status.OPEN, Grade.TEACHER, new Date(), "t_region3", Status.OPEN, "t_subject3", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", "t_certif3", true, "잘 부탁드립니다"); em.persist(teacher3);
            Teacher teacher4 = new Teacher("teacher4", "teacher4", "name4", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture4", Status.OPEN, Grade.TEACHER, new Date(), "t_region4", Status.OPEN, "t_subject4", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", "t_certif4", true, "잘 부탁드립니다"); em.persist(teacher4);
            Teacher teacher5 = new Teacher("teacher5", "teacher5", "name5", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture5", Status.OPEN, Grade.TEACHER, new Date(), "t_region5", Status.OPEN, "t_subject5", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", "t_certif5", true, "잘 부탁드립니다"); em.persist(teacher5);
            Teacher teacher6 = new Teacher("teacher6", "teacher6", "name6", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture6", Status.OPEN, Grade.TEACHER, new Date(), "t_region6", Status.OPEN, "t_subject6", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", "t_certif6", true, "잘 부탁드립니다"); em.persist(teacher6);
            Teacher teacher7 = new Teacher("teacher7", "teacher7", "name7", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture7", Status.OPEN, Grade.TEACHER, new Date(), "t_region7", Status.OPEN, "t_subject7", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", "t_certif7", true, "잘 부탁드립니다"); em.persist(teacher7);
            Teacher teacher8 = new Teacher("teacher8", "teacher8", "name8", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture8", Status.OPEN, Grade.TEACHER, new Date(), "t_region8", Status.OPEN, "t_subject8", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", "t_certif8", true, "잘 부탁드립니다"); em.persist(teacher8);
            Teacher teacher9 = new Teacher("teacher9", "teacher9", "name9", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture9", Status.OPEN, Grade.TEACHER, new Date(), "t_region9", Status.OPEN, "t_subject9", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", "t_certif9", true, "잘 부탁드립니다"); em.persist(teacher9);
            Teacher teacher10 = new Teacher("teacher10", "teacher10", "name10", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture10", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject10", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", "t_certif10", false, "잘 부탁드립니다"); em.persist(teacher10);
            Teacher teacher11 = new Teacher("teacher11", "teacher11", "name11", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture11", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject11", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", "t_certif11", false, "잘 부탁드립니다"); em.persist(teacher11);
            Teacher teacher12 = new Teacher("teacher12", "teacher12", "name12", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture12", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject12", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", "t_certif12", false, "잘 부탁드립니다"); em.persist(teacher12);
            Teacher teacher13 = new Teacher("teacher13", "teacher13", "name13", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture13", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject13", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", "t_certif13", false, "잘 부탁드립니다"); em.persist(teacher13);
            Teacher teacher14 = new Teacher("teacher14", "teacher14", "name14", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture14", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject14", 200000, "가천대", SchoolStatus.ABSENCE_OF_SCHOOL, "컴공", "t_certif14", false, "잘 부탁드립니다"); em.persist(teacher14);
            Teacher teacher15 = new Teacher("teacher15", "teacher15", "name15", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture15", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject15", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", "t_certif15", false, "잘 부탁드립니다"); em.persist(teacher15);
            Teacher teacher16 = new Teacher("teacher16", "teacher16", "name16", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture16", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject16", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", "t_certif16", false, "잘 부탁드립니다"); em.persist(teacher16);
            Teacher teacher17 = new Teacher("teacher17", "teacher17", "name17", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture17", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject17", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", "t_certif17", false, "잘 부탁드립니다"); em.persist(teacher17);
            Teacher teacher18 = new Teacher("teacher18", "teacher18", "name18", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture18", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject18", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", "t_certif18", false, "잘 부탁드립니다"); em.persist(teacher18);
            Teacher teacher19 = new Teacher("teacher19", "teacher19", "name19", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture19", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject19", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", "t_certif19", false, "잘 부탁드립니다"); em.persist(teacher19);
            Teacher teacher20 = new Teacher("teacher20", "teacher20", "name20", "email@naver.com", "01012341234", Sex.FEMALE, 2001, "t_picture20", Status.CLOSE, Grade.TEACHER, new Date(), "s_region", Status.CLOSE, "t_subject20", 200000, "가천대", SchoolStatus.GRADUATE, "컴공", "t_certif20", false, "잘 부탁드립니다"); em.persist(teacher20);

            PostTestPaper postTestPaper1 = new PostTestPaper(teacher1, "title1 ", "content1 ", 0L, new Date(), new Date(), Status.CLOSE); em.persist(postTestPaper1);
            PostTestPaper postTestPaper2 = new PostTestPaper(teacher2, "title2 ", "content2 ", 0L, new Date(), new Date(), Status.CLOSE); em.persist(postTestPaper2);
            PostTestPaper postTestPaper3 = new PostTestPaper(teacher3, "title3 ", "content3 ", 0L, new Date(), new Date(), Status.CLOSE); em.persist(postTestPaper3);
            PostTestPaper postTestPaper4 = new PostTestPaper(teacher4, "title4 ", "content4 ", 0L, new Date(), new Date(), Status.CLOSE); em.persist(postTestPaper4);
            PostTestPaper postTestPaper5 = new PostTestPaper(teacher5, "title5 ", "content5 ", 0L, new Date(), new Date(), Status.CLOSE); em.persist(postTestPaper5);
            PostTestPaper postTestPaper6 = new PostTestPaper(teacher6, "title6 ", "content6 ", 0L, new Date(), new Date(), Status.CLOSE); em.persist(postTestPaper6);
            PostTestPaper postTestPaper7 = new PostTestPaper(teacher7, "title7 ", "content7 ", 0L, new Date(), new Date(), Status.CLOSE); em.persist(postTestPaper7);
            PostTestPaper postTestPaper8 = new PostTestPaper(teacher8, "title8 ", "content8 ", 0L, new Date(), new Date(), Status.CLOSE); em.persist(postTestPaper8);
            PostTestPaper postTestPaper9 = new PostTestPaper(teacher9, "title9 ", "content9 ", 0L, new Date(), new Date(), Status.CLOSE); em.persist(postTestPaper9);
            PostTestPaper postTestPaper10 = new PostTestPaper(teacher10, "title10", "content10", 0L, new Date(), null, Status.OPEN); em.persist(postTestPaper10);
            PostTestPaper postTestPaper11 = new PostTestPaper(teacher11, "title11", "content11", 0L, new Date(), null, Status.OPEN); em.persist(postTestPaper11);
            PostTestPaper postTestPaper12 = new PostTestPaper(teacher12, "title12", "content12", 0L, new Date(), null, Status.OPEN); em.persist(postTestPaper12);
            PostTestPaper postTestPaper13 = new PostTestPaper(teacher13, "title13", "content13", 0L, new Date(), null, Status.OPEN); em.persist(postTestPaper13);
            PostTestPaper postTestPaper14 = new PostTestPaper(teacher14, "title14", "content14", 0L, new Date(), null, Status.OPEN); em.persist(postTestPaper14);
            PostTestPaper postTestPaper15 = new PostTestPaper(teacher15, "title15", "content15", 0L, new Date(), null, Status.OPEN); em.persist(postTestPaper15);
        }
    }
}
