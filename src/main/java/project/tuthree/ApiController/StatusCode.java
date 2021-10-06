package project.tuthree.ApiController;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusCode {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500),
    SERVICE_UNAVAILABLE(503),
    DB_ERROR(600);

    private int code;

    StatusCode(int code) {
        this.code = code;
    }
}
