package project.tuthree.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.tuthree.ApiController.JwtApiController;
import project.tuthree.ApiController.PostAdminApiController;
import project.tuthree.ApiController.StatusCode;
import project.tuthree.exception.NotEnoughGradeException;
import project.tuthree.exception.NotEnoughUserException;
import project.tuthree.ApiController.EmbeddedResponse.NotExistBadDataResultResponse;
@Slf4j
@RestControllerAdvice(basePackages = "project.tuthree.ApiController",
        basePackageClasses = {PostAdminApiController.class, JwtApiController.class})
public class ExceptionController {

    /** post delete 시 adminId 잘못됐는데 삭제가 된다. -> 당연하지... 아이디 확인도 안하니까...
     * id랑 헤더의 id 비교*/
    /**
     * 특정 글 조회 시 없는 아이디를 지면 nullpointerexception이 난다.
     */

//        log.error("[오류가 난 위치를 넣을 수는 없을까...]");

    @ExceptionHandler(value = {TransientPropertyValueException.class, HttpMessageNotReadableException.class})
    public NotExistBadDataResultResponse HttpMessageNotReadableController(HttpMessageNotReadableException e) {
        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
        return new NotExistBadDataResultResponse(StatusCode.BAD_REQUEST.getCode(), "잘못된 정보가 입력되었습니다.");
    }

    @ExceptionHandler(value = {IllegalStateException.class, IllegalArgumentException.class, UnexpectedRollbackException.class})
    public NotExistBadDataResultResponse IllegalArgumentController(IllegalArgumentException e) {
        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
        return new NotExistBadDataResultResponse(StatusCode.FORBIDDEN.getCode(), "데이터베이스에 대한 잘못된 접근입니다.");
    }

    @ExceptionHandler(value = {SignatureException.class, MalformedJwtException.class})
    public NotExistBadDataResultResponse MalformedJwtController(MalformedJwtException e) {
        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
                "\n** error code\n" + StatusCode.UNAUTHORIZED + "\n");
        return new NotExistBadDataResultResponse(StatusCode.UNAUTHORIZED.getCode(), "잘못된 토큰 정보입니다.");
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public NotExistBadDataResultResponse ExpiredJwtController(ExpiredJwtException e) {
        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
                "\n** error code\n" + StatusCode.UNAUTHORIZED + "\n");
        return new NotExistBadDataResultResponse(StatusCode.UNAUTHORIZED.getCode(), "토큰 정보가 만료되었습니다.");
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public NotExistBadDataResultResponse HttpRequestMethodNotSupportedController(HttpRequestMethodNotSupportedException e) {
        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
                "\n** error code\n" + StatusCode.BAD_REQUEST.getCode() + "\n");
        return new NotExistBadDataResultResponse(StatusCode.BAD_REQUEST.getCode(), "잘못된 요청입니다.");
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public NotExistBadDataResultResponse NullPointerController(NullPointerException e) {
        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
        return new NotExistBadDataResultResponse(StatusCode.FORBIDDEN.getCode(), "정보를 찾을 수 없습니다.");
    }

    @ExceptionHandler(value = {NotEnoughGradeException.class, NotEnoughUserException.class})
    public NotExistBadDataResultResponse NotEnoughGradeController(NotEnoughGradeException e) {
        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
        return new NotExistBadDataResultResponse(StatusCode.FORBIDDEN.getCode(), "접근 권한이 없는 사용자입니다.");
    }

}
