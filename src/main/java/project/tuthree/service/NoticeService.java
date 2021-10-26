package project.tuthree.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.tuthree.domain.user.Grade;
import project.tuthree.dto.user.ChildDTO;
import project.tuthree.service.push.RabbitService;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class NoticeService {

    private final RabbitService rabbitService;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class keywordPushDTO {
        String sender;
        String receiver;
        String name;
        String grade;
    }



    /** 자녀 등록 알림 */
    public void ParentChildRegisterNotice(keywordPushDTO dto) {
        rabbitService.rabbitParentChildKeywordProducer(dto);
    }

}
