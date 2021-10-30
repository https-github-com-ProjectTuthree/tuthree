package project.tuthree.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import project.tuthree.ApiController.StatusCode;
import project.tuthree.exception.NotEnoughGradeException;
import project.tuthree.exception.NotEnoughUserException;
import project.tuthree.ApiController.EmbeddedResponse.NotExistBadDataResultResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice(basePackages = "project.tuthree.ApiController")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public NotExistBadDataResultResponse MethodArgumentNotValidController(MethodArgumentNotValidException e, BindingResult result) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), result.getFieldError().getDefaultMessage()).toString());
        return new NotExistBadDataResultResponse(HttpStatus.BAD_REQUEST.value(), result.getFieldError().getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {TransientPropertyValueException.class, HttpMessageNotReadableException.class})
    public NotExistBadDataResultResponse HttpMessageNotReadableController(HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), "잘못된 정보가 입력되었습니다.").toString());
        return new NotExistBadDataResultResponse(HttpStatus.BAD_REQUEST.value(), "잘못된 정보가 입력되었습니다.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {IllegalStateException.class, IllegalArgumentException.class, UnexpectedRollbackException.class})
    public NotExistBadDataResultResponse IllegalArgumentController(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), "찾으시는 정보가 없거나 잘못된 정보를 입력하셨습니다.").toString());
        return new NotExistBadDataResultResponse(HttpStatus.FORBIDDEN.value(), "찾으시는 정보가 없거나 잘못된 정보를 입력하셨습니다.");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {SignatureException.class})
    public NotExistBadDataResultResponse SignatureController(SignatureException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), "잘못된 토큰 정보입니다.").toString());
        return new NotExistBadDataResultResponse(HttpStatus.UNAUTHORIZED.value(), "잘못된 토큰 정보입니다.");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {MalformedJwtException.class})
    public NotExistBadDataResultResponse MalformedJwtController(MalformedJwtException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), e.getMessage()).toString());
        return new NotExistBadDataResultResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {ExpiredJwtException.class})
    public NotExistBadDataResultResponse ExpiredJwtController(ExpiredJwtException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), e.getMessage()).toString());
        return new NotExistBadDataResultResponse(HttpStatus.UNAUTHORIZED.value(), "토큰 정보가 만료되었습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public NotExistBadDataResultResponse HttpRequestMethodNotSupportedController(HttpRequestMethodNotSupportedException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), e.getMessage()).toString());
        return new NotExistBadDataResultResponse(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {NullPointerException.class})
    public NotExistBadDataResultResponse NullPointerController(NullPointerException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), e.getMessage()).toString());
        return new NotExistBadDataResultResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {NotEnoughGradeException.class, NotEnoughUserException.class})
    public NotExistBadDataResultResponse NotEnoughGradeController(NotEnoughGradeException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), e.getMessage()).toString());
        return new NotExistBadDataResultResponse(HttpStatus.FORBIDDEN.value(), "접근 권한이 없는 사용자입니다.");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = {Exception.class})
    public NotExistBadDataResultResponse ExceptionController(Exception e) {
        HttpStatus status = HttpStatus.CONFLICT;
        log.info(new ExceptionResponse(status.value(), status.getReasonPhrase(), e.getMessage()).toString());
        return new NotExistBadDataResultResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @AllArgsConstructor
    class ExceptionResponse {
        private final LocalDateTime timestamp = LocalDateTime.now();
        private int status;
        private String error;
        private String exception;

        public String toString() {
            return "\n==============================" +
                    "\n        ERROR OCCURED!!        " +
                    "\n==============================" +
                    "\ntimestamp : " + this.timestamp + "" +
                    "\nstatus : " + this.status + "" +
                    "\nerror : " + this.error + "" +
                    "\nexception : " + this.exception + "" +
                    "\n==============================\n";
        }
    }

}
