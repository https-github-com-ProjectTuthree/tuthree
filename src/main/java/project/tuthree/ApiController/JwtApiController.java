package project.tuthree.ApiController;


import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import project.tuthree.controller.JwtController;
import project.tuthree.domain.user.Grade;
import project.tuthree.exception.NotEnoughGradeException;
import project.tuthree.exception.NotEnoughUserException;
import project.tuthree.repository.PostTestPaperRepository;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static project.tuthree.configuration.Utils.*;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtApiController {
    /**
     * api 테스트를 위해 token 로직은 잠시 종료 해 둠
     */


    private final JwtController jwtController;
    private final PostTestPaperRepository postTestPaperRepository;

    private final HttpServletResponse response;
    private final HttpServletRequest request;


    /**
     * 글 조회 : 올바른 토큰인가? 올바른 권한을 가졌는가? -> 모든 권한에 적용(채팅은 잘 모르겠다.).
     * 글 작성 : 올바른 토큰인가? 올바른 권한을 가졌는가? 글의 작성자와 토큰의 아이디가 일치하는가?-> 선생님, 관리자 , 학생
     * => 전송된 글의 정보 중 아이디가 토큰의 아이디와 일치하는가?
     * 글 삭제 : 올바른 토큰인가? 올바른 권한을 가졌는가? 글의 작성자와 토큰의 아이디가 일치하는가? -> 선생님, 관리자 , 학생
     * => 들어온 아이디의 글과 사용자가 일치하는가?
     * 글 수정 : 올바른 토큰인가? 올바른 권한을 가졌는가? 글의 작성자와 토큰의 아이디가 일치하는가? -> 선생님, 관리자 , 학생
     * => 들어온 아이디의 글과 사용자가 일치하는가?
     */

    /**
     *
     * 토큰 파싱이 안될 때 로그인해주세요.
     * 올바른 토큰인지만 확인 / grade 권한 확인
     */

    /** 회원가입, findid, login, findpwd, changepwd은 할 필요 없다 */
    /**
     * adminapi - o
     * classmanage - alluer
     * classmatching - alluser
     * postadminapi - o
     * postuserapi - alluser
     * userapi - alluser
     */

//
//    @Around("execution(* PostUserApiController.Community*(..)) ||" +
//            "execution(* PostUserApiController.Faq*(..)) ||" +
//            "execution(* PostUserApiController.Notice*(..)) ||" +
//            "execution(* ClassMatchingApiController.findT*(..)) ||" +
//            "execution(* ClassMatchingApiController.*BookMark(..)) ||" +
//            "execution(* PostUserApiController.viewFaqUpdate(..))")
//    public Object CheckNormalUserFirst(final ProceedingJoinPoint joinPoint) throws Throwable {
//        //checknoramaluser 함수에 grade을 적용하지 위한 함수
//        Map<String, Object> objectMap = CheckUserGrade(Grade.ALL);
//        Object result = joinPoint.proceed();
//        makeRefreshToken(objectMap);
//        return result;
//    }
//
//    @Around("execution(* ClassManageApiController.FindUserSchedule(..)) ||" +
//            "execution(* ClassManageApiController.List*(..)) ||" +
//            "execution(* ClassManageApiController.FindPostById(..)) ||" +
//            "execution(* ClassManageApiController.*RealAnswer(..)) ||" +
//            "execution(* ClassMatchingApiController.*eStudyRoomInfo(..)) ||" +
//            "execution(* PostUserApiController.DownloadFile(..)) ||" +
//            "execution(* UserApiController.UserLogout(..)) ||" +
//            "execution(* UserApiController.findUserInfo(..)) ||" +
//            "execution(* UserApiController.update(..)) ||" +
//            "execution(* UserApiController.delete(..))")
//    public Object CheckAllUserFirst(final ProceedingJoinPoint joinPoint) throws Throwable {
//        //checknoramaluser 함수에 grade을 적용하지 위한 함수
//        Map<String, Object> objectMap = CheckUserGrade(Grade.USERALL);
//        Object result = joinPoint.proceed();
//        makeRefreshToken(objectMap);
//        return result;
//    }
//
//    @Around("execution(* ClassManageApiController.WriteStudyRoomReview(..)) ||" +
//            "execution(* ClassManageApiController.FindStudyRoomByChild(..)) ||" +
//            "execution(* ClassMatchingApiController.findStudyRoomInfo(..)) ||" +
//            "execution(* UserApiController.AcceptChild(..)) ||" +
//            "execution(* UserApiController.findTuteeInfo(..)) ||" +
//            "execution(* UserApiController.studentUpdate(..))")
//    public Object CheckStudentuserFirst(final ProceedingJoinPoint joinPoint) throws Throwable {
//        //checknoramaluser 함수에 grade을 적용하지 위한 함수
//        Map<String, Object> objectMap = CheckUserGrade(Grade.STUDENT);
//        Object result = joinPoint.proceed();
//        makeRefreshToken(objectMap);
//        return result;
//    }
//
//    @Around("execution(* UserApiController.findTutorInfo(..)) ||" +
//            "execution(* UserApiController.teacherUpdate(..))")
//    public Object CheckTeacherUserFirst(final ProceedingJoinPoint joinPoint) throws Throwable {
//        Map<String, Object> objectMap = CheckUserGrade(Grade.TEACHER);
//        Object result = joinPoint.proceed();
//        makeRefreshToken(objectMap);
//        return result;
//    }
//
//    @Around("execution(* UserApiController.PlusChild(..))")
//    public Object CheckParentUserFirst(final ProceedingJoinPoint joinPoint) throws Throwable {
//        Map<String, Object> objectMap = CheckUserGrade(Grade.PARENT);
//        Object result = joinPoint.proceed();
//        makeRefreshToken(objectMap);
//        return result;
//    }
//
//    @Around("execution(* ClassManageApiController.FindStudyRoomByOne(..)) ||" +
//            "execution(* ClassManageApiController.FindStudyRoomByIds(..)) ||" +
//            "execution(* ClassManageApiController.Register*(..)) ||" +
//            "execution(* ClassManageApiController.Update*(..)) ||" +
//            "execution(* ClassManageApiController.Delete*(..)) ||" +
//            "execution(* ClassMatchingApiController.StudyRoom*(..))")/
//    public Object CheckTeacherOrStudentUserFirst(final ProceedingJoinPoint joinPoint) throws Throwable {
//        Map<String, Object> objectMap = new HashMap<>();
//        try{
//            objectMap = CheckUserGrade(Grade.TEACHER);
//        } catch (Exception e) {
//            objectMap = CheckUserGrade(Grade.STUDENT);
//        }
//
//        Object result = joinPoint.proceed();
//        makeRefreshToken(objectMap);
//        return result;
//    }

//    @Around("execution(* AdminApiController.*(..)) ||" +
//            "execution(* PostAdminApiController.*(..))")
//    public Object CheckAdminFirst(final ProceedingJoinPoint joinPoint) throws Throwable {
//        Map<String, Object> objectMap = CheckUserGrade(Grade.ADMIN);
//        Object result = joinPoint.proceed();
//        makeRefreshToken(objectMap);
//        return result;
//    }


    //호출하면 jooint가 되지 않는다.따로 만들기
    public Map<String, Object> CheckUserGrade(Grade userGrade) throws Throwable {
        /** 권한 입력 받기 : teacher, student, parent, all */
        Map<String, Object> map = new HashMap<>();
        String[] requestToken = request.getHeader(AUTHORIZATION).split(" ");

        if(!requestToken[0].equals(BEARER)) {
            if (userGrade.equals(Grade.ALL)) {
                map.put(CLAIMGRADE, "all");
                return map;
            }
            throw new MalformedJwtException("먼저 로그인해주세요.");
        }

        try {
            map = jwtController.decryptValidJwtToken(requestToken[1]);

            String userId = String.valueOf(map.get(CLAIMUSERID));
            String grade = String.valueOf(map.get(CLAIMGRADE));
            log.info("\nuser id : " + userId + "\nuser grade : " + grade);

            if (userId == null || grade == null) throw new MalformedJwtException("토큰 형식이 맞지 않습니다.");

            // token 형식 체크 끝
            //usergrade가 userall일 때 parent, student, teacher 중 하나인지 확인
            if (((userGrade.equals(Grade.USERALL) || userGrade.equals(Grade.ALL)) && (grade.equals(Grade.PARENT.getStrType()) || grade.equals(Grade.STUDENT.getStrType()) || grade.equals(Grade.TEACHER.getStrType()) || grade.equals(Grade.ADMIN.getStrType()))) || (grade.equals(userGrade.getStrType()))) {
                return map;
            }
            throw new NotEnoughGradeException();

        } catch (IndexOutOfBoundsException e) {
            throw new MalformedJwtException("먼저 로그인해주세요.");
        } catch (MalformedJwtException | SignatureException | NullPointerException e) {
            throw new MalformedJwtException("잘못된 토큰입니다.");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    /** refresh token */
    public void makeRefreshToken(Map<String, Object> map) {
        String grade = String.valueOf(map.get(CLAIMGRADE));
        if(grade.equals(Grade.ALL.getStrType())) return;
        String userId = String.valueOf(map.get(CLAIMUSERID));
        String responseToken = jwtController.makeJwtToken(userId, grade);
        response.setHeader(AUTHORIZATION, BEARER + " " + responseToken);
        log.info("CheckNormalUser // seperate test");
    }

//
//    /** 관리자 작성 - 관리자 권한인지 확인 */
//    @Around("execution(* PostAdminApiController.*(..))")
//    public Object CheckAdminJwtToken(final ProceedingJoinPoint joinPoint) throws Throwable {
//        //관리자 작성, 수정, 삭제 - admin 계정이 하나 밖에 없어서 어차피 다른 계정으로 로그인도 못함
//        //올바른 토큰인가?
//        String[] requestToken = request.getHeader(AUTHORIZATION).split(" ");
//        if(!requestToken[0].equals(BEARER)){
//            throw new MalformedJwtException("잘못된 토큰정보입니다.");
//        }
//
//        Map<String, Object> map = jwtController.decryptValidJwtToken(requestToken[1]);
//        log.info(jwtController.decryptValidJwtToken(requestToken[1]).toString());
//
//        String userId = String.valueOf(map.get("userId"));
//        String grade = String.valueOf(map.get("Grade"));
//
//        Assert.assertEquals(Grade.ADMIN.getStrType(), grade);//권한이 관리자인가?
////        adminRepository.findById(userId); -> 사용자 아이디가 존재하는가 외래키가 알아서 할 것
//
//        Object result = joinPoint.proceed();
//
//        String responseToken = jwtController.makeJwtToken(userId, grade);
//        response.setHeader(AUTHORIZATION, BEARER + " " + responseToken);
//        log.info("CheckAdminWriteJwtToken");
//        return result;
//    }
//
//    /** 선생님 작성 - 선생님 권한인지 확인 */
//    @Around("execution(* PostUserApiController.writeCommunity(..))")
//    public Object CheckTeacherJwtToken(final ProceedingJoinPoint joinPoint) throws Throwable {
//        //선생님 작성
//        //올바른 토큰인가?
//        String[] requestToken = request.getHeader(AUTHORIZATION).split(" ");
//        if(!requestToken[0].equals(BEARER)){
//            throw new MalformedJwtException("잘못된 토큰정보입니다.");
//        }
//        Map<String, Object> map = jwtController.decryptValidJwtToken(requestToken[1]);
//        log.info(jwtController.decryptValidJwtToken(requestToken[1]).toString());
//
//        String userId = String.valueOf(map.get(CLAIMUSERID));
//        String grade = String.valueOf(map.get(CLAIMGRADE));
//
//        Assert.assertEquals(Grade.TEACHER.getStrType(), grade);//권한이 관리자인가?
//
//        Object result = joinPoint.proceed();
//
//        String responseToken = jwtController.makeJwtToken(userId, grade);
//        response.setHeader(AUTHORIZATION, BEARER + " " + responseToken);
//        log.info("CheckTeacherJwtToken");
//        return result;
//    }
//
//    /** 선생님 수정, 삭제 - 선생님 권한인지 확인, 원글의 작성자와 일치하는지 확인 */
//    @Around("execution(* PostUserApiController.deleteCommunity(..)) ||" +
//            "execution(* PostUserApiController.updateCommunity(..))")
//    public Object CheckTeacherAlterJwtToken(final ProceedingJoinPoint joinPoint) throws Throwable {
//        //선생님 작성
//        //올바른 토큰인가?
//        String[] requestToken = request.getHeader(AUTHORIZATION).split(" ");
//        if(!requestToken[0].equals(BEARER)){
//            throw new MalformedJwtException("잘못된 토큰정보입니다.");
//        }
//        Map<String, Object> map = jwtController.decryptValidJwtToken(requestToken[1]);
//        log.info(jwtController.decryptValidJwtToken(requestToken[1]).toString());
//
//        String userId = String.valueOf(map.get(CLAIMUSERID));
//        String grade = String.valueOf(map.get(CLAIMGRADE));
//
//        if(grade.equals(Grade.TEACHER.getStrType())){
//            //선생님 권한??
//            String pathvariable = String.valueOf(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
//            pathvariable = pathvariable.replaceAll("\\{", "").replaceAll("}", "");
//            Long id = Long.parseLong(pathvariable.split("=")[1]);
//
//            if (!postTestPaperRepository.findById(id).getUserId().getId().equals(userId)) {
//                throw new NotEnoughUserException();
//            }//토큰의 아이디가 원글의 아이디와 동일한가?
//        } else if(!grade.equals(Grade.ADMIN.getStrType())){
//            throw new NotEnoughUserException();
//        }
//        Object result = joinPoint.proceed();
//
//        String responseToken = jwtController.makeJwtToken(userId, grade);
//        response.setHeader(AUTHORIZATION, BEARER + " " + responseToken);
//        log.info("CheckTeacherAlterJwtToken");
//        return result;
//    }
/**
    //종료 시 값 실어서 보내는 로직으로 만들기
    @After("execution(* PostAdminApiController.*(..))")
    public void createRefreshJwtToken() {
        String userId = "";
        String grade = Grade.PARENT.getStrType();
        String Token = jwtController.makeJwtToken(userId, grade);
        String BarerToken = "Barer " + Token;
        response.setHeader("Authorization", BarerToken);
    }


    @Before("execution(* PostAdminApiController.*(..))")
    public void checkAdminValidToken() throws JsonParseException {

        String[] token = request.getHeader("Authorization").split(" ");


        //관리자 로그인, 로그아웃
        //관리자 faq 조회, 수정, 삭제, 특정 글 조회 - 공지사항도 마찬가지  =>  faq에 전달된 아이디랑 비교해서 알아야 하나...
        Map<String, Object> map = jwtController.decryptValidJwtToken(token[1]);
        log.info("\nmap : " + map.toString());
        Assert.assertEquals(Grade.ADMIN.getStrType(), map.get("Grade"));
        adminRepository.findById(String.valueOf(map.get("userId")));

    }
*/

    class TokenTemp{
        String userId;
        String grade;
    }

    @RestController
    class example {
        @GetMapping("/token/{grade}")
        public Map<String, String> exampleToken(@PathVariable("grade") String pathGrade) {
            String id = "";
            String grade = "";
            if(pathGrade.equals("admin")){
                id = "admin1";
                grade = Grade.ADMIN.getStrType();
            } else if(pathGrade.equals("teacher")){
                id = "teacher1";
                grade = Grade.TEACHER.getStrType();
            }
            String Token = jwtController.makeJwtToken(id, grade);
            String BearerToken = BEARER + " " + Token;
            Map<String, String> token = new HashMap<>();
            token.put(AUTHORIZATION, BearerToken);
            log.info(jwtController.decryptValidJwtToken(Token).toString());
            return token;
        }
        @GetMapping("/token/{grade}/{userId}")
        public Map<String, String> exampleToken(@PathVariable("userId") String userId, @PathVariable("grade") String pathGrade) {

            String Token = jwtController.makeJwtToken(userId, pathGrade);
            String BearerToken = BEARER + " " + Token;
            Map<String, String> token = new HashMap<>();
            token.put(AUTHORIZATION, BearerToken);
            log.info(jwtController.decryptValidJwtToken(Token).toString());
            return token;
        }
    }
}

