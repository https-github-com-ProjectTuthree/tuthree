package project.tuthree.service.push;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.ChatDTO;
import project.tuthree.mapper.ChatMapper;
//import project.tuthree.repository.ChatRepository;
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

    @RabbitListener(queues = "chat", concurrency = "6")
    public void pushChatConsumer( final ChatDTO chatDTO) {
        //알림 보내고
        pushService.sendByToken(chatDTO);
        //디비에 저장
        log.info(chatDTO.toString());
        chatRepository.saveChatLog(chatMapper.toEntity(chatDTO));
    }
}
