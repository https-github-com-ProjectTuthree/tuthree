//package project.tuthree.controller;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.TransientPropertyValueException;
//import org.junit.Assert;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.transaction.UnexpectedRollbackException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import project.tuthree.ApiController.StatusCode;
//
//@Slf4j
//@RestControllerAdvice(annotations = RestController.class)
//public class ExceptionController{
//
//    /** post delete 시 adminId 잘못됐는데 삭제가 된다. -> 당연하지... 아이디 확인도 안하니까...
//     * id랑 헤더의 id 비교*/
//    /** 특정 글 조회 시 없는 아이디를 지면 nullpointerexception이 난다. */
//
//    @Getter
//    @AllArgsConstructor
//    class NotExistDataResultResponse<T>{
//        private final Boolean Success = false;
//        int StatusCode;
//        String Message;
//    }
//
////        log.error("[오류가 난 위치를 넣을 수는 없을까...]");
//
//    @ExceptionHandler(TransientPropertyValueException.class)
//    public NotExistDataResultResponse TransientPropertyValueController(TransientPropertyValueException e) {
//        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
//                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
//        return new NotExistDataResultResponse(StatusCode.BAD_REQUEST.getCode(), "잘못된 정보가 입력되었습니다.");
//    }
//
//    @ExceptionHandler(value = {UnexpectedRollbackException.class, IllegalArgumentException.class})
//    public NotExistDataResultResponse UnexpectedRollbackController(UnexpectedRollbackException e) {
//        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
//                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
//        return new NotExistDataResultResponse(StatusCode.FORBIDDEN.getCode(), "데이터베이스에 대한 잘못된 접근입니다.");
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public NotExistDataResultResponse HttpMessageNotReadableController(HttpMessageNotReadableException e) {
//        log.error("\n** error occurred!!!!\n[ " + String.valueOf(e) + " ] " + e.getMessage() +
//                "\n** error code\n" + StatusCode.FORBIDDEN.getCode() + "\n");
//        return new NotExistDataResultResponse(StatusCode.BAD_REQUEST.getCode(), "잘못된 정보가 입력되었습니다.");
//    }
//
//}
