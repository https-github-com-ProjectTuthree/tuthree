package project.tuthree.ApiController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.tuthree.domain.user.Category;

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
    public static class ExistDoubleDataSuccessResponse<T>{
        private final Boolean Success = true;
        int StatusCode;
        String Message;
        T list;
        T data;
    }

    @Getter
    @AllArgsConstructor
    public static class NotExistDataResultResponse{
        private final Boolean Success = true;
        int StatusCode;
        String Message;
    }

    @Getter
    @AllArgsConstructor
    public static class NotExistBadDataResultResponse {
        private final Boolean Success = false;
        int StatusCode;
        String Message;
    }

    @Getter
    public static class RestResponse {
        private boolean success;
        private Category data;
        public RestResponse(boolean success, Category data) {
            this.success = success;
            this.data = data;
        }
    }
}
