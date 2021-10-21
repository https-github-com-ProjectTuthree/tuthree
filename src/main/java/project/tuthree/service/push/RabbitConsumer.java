package project.tuthree.service.push;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.ChatDTO;
import project.tuthree.repository.UserEntityRepository;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitConsumer {
    private final PushService pushService;
    private final UserEntityRepository userEntityRepository;

    @RabbitListener(queues = "chat-queue", concurrency = "6")
    public void pushConsumer(final Message message, Queue queue) throws FirebaseMessagingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatDTO chatDTO = objectMapper.readValue(message.getBody(), ChatDTO.class);
        System.out.println("content = " + message.getBody());
        System.out.println("queue = " + queue);
        pushService.sendByToken(chatDTO);
        //채팅 로그 남기기
    }

    /** example */

//    @RabbitListener(queues = "chat-queue", concurrency = "6")
//    public void pushChatConsumer(final Message message) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ChatRequestDto chatRequestDto = objectMapper.readValue(message.getBody(), ChatRequestDto.class);
//        pushService.sendByToken(chatRequestDto);
//        chatService.addChatLog(chatRequestDto);
//    }
}
