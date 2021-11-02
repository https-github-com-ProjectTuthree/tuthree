package project.tuthree.service.push;

import com.google.firebase.messaging.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tuthree.service.NoticeService.keywordPushDTO;
import project.tuthree.controller.RedisTestService;
import project.tuthree.domain.user.Grade;
import project.tuthree.dto.ChatDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushService {
    private final RedisTestService redisTestService;

    /** 토큰 조회 */
    public String getFcmToken(String userId) {
        return redisTestService.getRedisStringValue(userId);
    }

    public void sendChatByToken(ChatDTO chatDTO) {
        String fcmToken = getFcmToken(chatDTO.getUserId());
        log.info("hello============" + fcmToken);
        if (fcmToken != null) {
            WebpushNotification notification = WebpushNotification.builder()
                    .setTitle(chatDTO.getName() + "님으로부터 메시지가 왔습니다.")
                    .setBody(chatDTO.getContent())
                    .build();

            WebpushConfig config = WebpushConfig.builder()
                    .setNotification(notification)
                    .build();

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setWebpushConfig(config)
                    .build();

            FirebaseMessaging.getInstance().sendAsync(message);
            log.info("firebase 메시지 전송");
        }
    }


    public void sendKeywordByToken(keywordPushDTO keywordPushDTO) {
        String fcmToken = getFcmToken(keywordPushDTO.getReceiver());
        if (fcmToken != null) {
            String title = "";
            String body = "";
            if(keywordPushDTO.getGrade().equals(Grade.PARENT.getStrType())){
                //보내는 사람이 부모 - 요청 메시지
                body = keywordPushDTO.getName() + "님께서 자녀 등록 요청을 보냈습니다.";
                title = "Tuthree 과외 서비스 자녀 등록 요청";
            } else if (keywordPushDTO.getGrade().equals(Grade.STUDENT.getStrType())) {
                //보내는 사람이 학생 - 수락 메시지
                body = keywordPushDTO.getName() + "님이 자녀로 등록되었습니다.";
                title = "Tuthree 과외 서비스 자녀 등록 성공";
            }
            WebpushNotification notification = WebpushNotification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            WebpushConfig config = WebpushConfig.builder()
                    .setNotification(notification)
                    .build();

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setWebpushConfig(config)
                    .build();

            FirebaseMessaging.getInstance().sendAsync(message);
            log.info("firebase keyword :: PC Register 메시지 전송");
        }
    }
}
