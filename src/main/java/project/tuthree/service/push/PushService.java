package project.tuthree.service.push;

import com.google.firebase.messaging.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.controller.RedisTestService;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.ChatDTO;
import project.tuthree.repository.UserEntityRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushService {
    private final UserEntityRepository userEntityRepository;
    private final RedisTestService redisTestService;

    //String fcmToken = "e4XGMOVOQhY:APA91bEdPah3AuW5pshLafwEYOQxWvm2Pb8KDhn850kcmJvziZvZjfOsl1YytOUh3Cuf93b7i1CXM0gPV16GXsH5MN-sYMF29a-K6hNqmrbgXAyeBQnrWoaVsihHxc5WHlTPzE_McK5D";

    /** 토큰 조회 */
    public String getFcmToken(String userId) {
        //userid로 토큰 찾기
        log.info("========================" + userId);

        return redisTestService.getRedisStringValue(userId);
    }

    public void sendByToken(ChatDTO chatDTO) {
        String fcmToken = getFcmToken(chatDTO.getUserId());
        if (fcmToken != null) {
            WebpushNotification notification = WebpushNotification.builder()
                    .setTitle(chatDTO.getName() + "님의 메시지")
                    .setBody("content :: " + chatDTO.getContent())
//                    .setTag(chatDTO.getRoom().getId().toString())
//                    .setTag(chatDTO.getId().toString())
                    .build();

            WebpushConfig config = WebpushConfig.builder()
                    .setNotification(notification)
                    .build();

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setWebpushConfig(config)
                    .putData("sender", chatDTO.getUserId())
//                    .putData("receiver", chatDTO.getRoom().toString())////////////
                    .putData("content", chatDTO.getContent())
//                    .putData("roomId", chatDTO.getRoom().toString())
//                    .putData("roomId", chatDTO.getId().toString())
                    .build();

            FirebaseMessaging.getInstance().sendAsync(message);
            log.info("firebase 메시지 전송");
        }
    }

    @Transactional
    public void setTopic(Teacher teacher, String topic) throws FirebaseMessagingException {
        String encodedTopic = topic; //encoding??
        String fcmToken = getFcmToken(teacher.getId());
        if (fcmToken != null) {
            List<String> rt = Collections.singletonList(fcmToken);
            FirebaseMessaging.getInstance().subscribeToTopic(rt, encodedTopic);
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationResponse {
        private Long productId;
        private String topic;
    }

    @Transactional
    public void deleteTopic(NotificationResponse response) {
        String encodedTopic = response.getTopic();
        String sendTitle = encodedTopic + "알림 도착";
        String sendMessage = encodedTopic + "에 해당하는 물품 등록/ 채팅 전송";
        WebpushNotification notification = WebpushNotification.builder()
                .setTitle(sendTitle)
                .setBody(sendMessage)
                .setTag(response.getProductId().toString())
                .build();

        WebpushConfig config = WebpushConfig.builder()
                .setNotification(notification)
                .build();

        Message message = Message.builder()
                .setTopic(encodedTopic)
                .setWebpushConfig(config)
                .putData("id", response.getProductId().toString())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);

        String receivers = getReceiverByKeyword(response.getProductId());
        //나는 프로덕트 아이디가 아니라 roomId가 될 것
    }

    //이 사람은 멤버키워드라는 클래스 목록을 반환
    //나는 룸 아이디만 반환하면 될 듯
    @Transactional
    public String getReceiverByKeyword(Long id) {
        //해당 채팅방의 회원 찾기
        //String roomId =  회원 이름 하나 반환
        return "";
    }

    //키워드는 그냥 이 분이 등록한거고...키워드 등록 토픽 등록 다른가...

}
