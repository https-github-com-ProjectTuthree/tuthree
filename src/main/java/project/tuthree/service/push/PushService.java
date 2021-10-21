package project.tuthree.service.push;

import com.google.firebase.messaging.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.ChatDTO;
import project.tuthree.repository.ChatRepository;
import project.tuthree.repository.UserEntityRepository;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushService {
    private final UserEntityRepository userEntityRepository;

    /** 토큰 조회 */
    public String getFcmToken(String userId) {
        Teacher teacher = userEntityRepository.teacherFindById(userId);
        return teacher.getName(); //token
    }

    public void sendByToken(ChatDTO chatDTO) {
        String fcmToken = getFcmToken(chatDTO.getUserId());
        if (fcmToken != null) {
            WebpushNotification notification = WebpushNotification.builder()
                    .setTitle(chatDTO + "님의 메시지")
                    .setBody("content :: " + chatDTO.getContent())
                    .setTag(chatDTO.getRoom().toString())
                    .build();

            WebpushConfig config = WebpushConfig.builder()
                    .setNotification(notification)
                    .build();

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setWebpushConfig(config)
                    .putData("sender", chatDTO.getUserId())
                    .putData("receiver", chatDTO.getRoom().toString())////////////
                    .putData("content", chatDTO.getContent())
                    .putData("roomId", chatDTO.getRoom().toString())
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
