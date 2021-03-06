package project.tuthree.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.tuthree.domain.Status;


public class EmbeddedDTO {

    @Getter
    @AllArgsConstructor
    public static class LoginReturnDTO{
        String id;
        String name;
        String grade;
    }

    @Getter
    @AllArgsConstructor
    public static class PostListDTO {
        Long id;
        String userId;
        String title;
        String writeAt;
        Long view;
        //계정 아이디 외의 다른 정보를 노출시키지 않기 위해 만든 클래스
    }

    @Getter
    @AllArgsConstructor
    public static class PostListTypeDTO {
        Long id;
        String userId;
        String title;
        String writeAt;
        String type;
        Long view;
        //계정 아이디 외의 다른 정보를 노출시키지 않기 위해 만든 클래스
    }

    @Getter
    @AllArgsConstructor
    public static class PostSingleContentDTO {
        Long id;
        String userId;
        String title;
        String content;
        Long view;
        String writeAt;
        Status secret;
        //계정 아이디 외의 다른 정보를 노출시키지 않기 위해 만든 클래스
    }

    @Getter
    @AllArgsConstructor
    public static class PostSingleContentTypeDTO {
        Long id;
        String userId;
        String title;
        String content;
        Long view;
        String writeAt;
        String type;
        Status secret;
        //계정 아이디 외의 다른 정보를 노출시키지 않기 위해 만든 클래스
    }

}
