//package project.tuthree.controller;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.TransientPropertyValueException;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.transaction.UnexpectedRollbackException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import project.tuthree.ApiController.JwtApiController;
//import project.tuthree.ApiController.PostAdminApiController;
//import project.tuthree.ApiController.StatusCode;
//import project.tuthree.repository.PostFaqRepository;
//import project.tuthree.repository.PostNoticeRepository;
//import project.tuthree.service.PostFaqService;
//import project.tuthree.service.PostNoticeService;
//
//@Slf4j
//@RestControllerAdvice(basePackages = "project.tuthree.ApiController",
//        basePackageClasses = {PostAdminApiController.class, PostFaqRepository.class, PostFaqService.class,
//                PostNoticeRepository.class, PostNoticeService.class, JwtApiController.class,
//        JwtController.class})
//public class ExceptionController {
//
//    /** post delete 시 adminId 잘못됐는데 삭제가 된다. -> 당연하지... 아이디 확인도 안하니까...
//     * id랑 헤더의 id 비교*/
//    /**
//     * 특정 글 조회 시 없는 아이디를 지면 nullpointerexception이 난다.
//     */
//
//    @Getter
//    @AllArgsConstructor
//    class NotExistDataResultResponse {
//        private final Boolean Success = false;
//        int StatusCode;
//        String Message;
//    }
//
////        log.error("[오류가 난 위치를 넣을 수는 없을까...]");
//
//    @ExceptionHandler(value = {TransientPropertyValueException.class, HttpMessageNotReadableException.class})
//    public NotExistDataResultResponse HttpMessageNotReadableController(HttpMessageNotReadableException e) {
//        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
//                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
//        return new NotExistDataResultResponse(StatusCode.BAD_REQUEST.getCode(), "잘못된 정보가 입력되었습니다.");
//    }
//
//    @ExceptionHandler(value = {IllegalArgumentException.class, UnexpectedRollbackException.class})
//    public NotExistDataResultResponse IllegalArgumentController(IllegalArgumentException e) {
//        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
//                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
//        return new NotExistDataResultResponse(StatusCode.FORBIDDEN.getCode(), "데이터베이스에 대한 잘못된 접근입니다.");
//    }
//
//    @ExceptionHandler(value = {SignatureException.class, MalformedJwtException.class})
//    public NotExistDataResultResponse MalformedJwtController(MalformedJwtException e) {
//        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
//                "\n** error code\n" + StatusCode.UNAUTHORIZED + "\n");
//        return new NotExistDataResultResponse(StatusCode.UNAUTHORIZED.getCode(), "잘못된 토큰 정보입니다.");
//    }
//
//    @ExceptionHandler(value = {ExpiredJwtException.class})
//    public NotExistDataResultResponse ExpiredJwtController(ExpiredJwtException e) {
//        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
//                "\n** error code\n" + StatusCode.UNAUTHORIZED + "\n");
//        return new NotExistDataResultResponse(StatusCode.UNAUTHORIZED.getCode(), "토큰 정보가 만료되었습니다.");
//    }
//
//    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
//    public NotExistDataResultResponse HttpRequestMethodNotSupportedController(HttpRequestMethodNotSupportedException e) {
//        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
//                "\n** error code\n" + StatusCode.BAD_REQUEST.getCode() + "\n");
//        return new NotExistDataResultResponse(StatusCode.BAD_REQUEST.getCode(), "잘못된 요청입니다.");
//    }
//
//    @ExceptionHandler(value = {NullPointerException.class})
//    public NotExistDataResultResponse NullPointerController(NullPointerException e) {
//        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
//                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
//        return new NotExistDataResultResponse(StatusCode.BAD_REQUEST.getCode(), "정보를 찾을 수 없습니다.");
//    }
//
//}
