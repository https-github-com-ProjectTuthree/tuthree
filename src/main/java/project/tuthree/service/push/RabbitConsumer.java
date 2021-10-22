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
import project.tuthree.mapper.ChatMapper;
import project.tuthree.repository.ChatRepository;
import project.tuthree.repository.UserEntityRepository;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitConsumer {
    private final PushService pushService;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @RabbitListener(queues = "chat-queue", concurrency = "6")
    public void pushChatConsumer(final Message message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatDTO chatDTO = objectMapper.readValue(message.getBody(), ChatDTO.class);
        pushService.sendByToken(chatDTO);
        chatRepository.saveChatLog(chatMapper.toEntity(chatDTO));
    }
}
