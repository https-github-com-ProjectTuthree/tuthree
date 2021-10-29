package project.tuthree.service.push;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import project.tuthree.service.NoticeService.keywordPushDTO;
import project.tuthree.dto.ChatDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitConsumer {
    private final PushService pushService;

    @RabbitListener(queues = "chat", concurrency = "6")
    public void pushChatConsumer(final ChatDTO chatDTO) {
        pushService.sendChatByToken(chatDTO);
    }

    @RabbitListener(queues = "keyword", concurrency = "6")
    public void pushKeywordConsumer(final keywordPushDTO dto) {
        pushService.sendKeywordByToken(dto);
    }
}
