package project.tuthree.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.tuthree.domain.Status;

import java.util.Date;

public class EmbeddedDTO {

    @Getter
    @AllArgsConstructor
    public static class PostListDTO {
        Long id;
        String userId;
        String title;
        String content;
        Long view;
        Date writeAt;
        Date alterAt;
        Status secret;
        //계정 아이디 외의 다른 정보를 노출시키지 않기 위해 만든 클래스
    }

    @Getter
    @AllArgsConstructor
    public static class FileListDto<T> {
        T data;
    }


    @Getter
    @AllArgsConstructor
    public static class PostListData<T> {
        Long list;
        T data; //postlistdto
    }


}
