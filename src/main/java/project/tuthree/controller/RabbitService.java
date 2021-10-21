package project.tuthree.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import project.tuthree.dto.ChatDTO;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RabbitService {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Note {
        private Long id;
        private String topic;
    }

    private final RabbitTemplate rabbitTemplate;
    private static final String exchange = "chat-exchange";

    public void sendNotification(String topic, Long id) throws FirebaseMessagingException {

        Note note = Note.builder()
                .topic(topic)
                .id(id)
                .build();
        rabbitTemplate.convertAndSend(exchange, "push.keyword." + String.valueOf(id), note);

    }

    public void rabbitChatProducer(ChatDTO chatDTO) {
        rabbitTemplate.convertAndSend(exchange, "push.chat." + String.valueOf(chatDTO.getRoom().getId()), chatDTO);
    }
}
