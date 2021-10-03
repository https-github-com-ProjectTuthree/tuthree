package project.tuthree.ApiController;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EmbeddedResponse {

    @Getter
    @AllArgsConstructor
    public static class ExistDataSuccessResponse<T>{
        private final Boolean Success = true;
        int StatusCode;
        String Message;
        T data;
    }

    @Getter
    @AllArgsConstructor
    public static class ExistListDataSuccessResponse<T>{
        private final Boolean Success = true;
        int StatusCode;
        String Message;
        Long list;
        T data;
    }

    @Getter
    @AllArgsConstructor
    public static class NotExistDataResultResponse{
        private final Boolean Success = true;
        int StatusCode;
        String Message;
    }



}
