package project.tuthree.service.push;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.service.NoticeService.keywordPushDTO;
import project.tuthree.dto.ChatDTO;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RabbitService {

    private static final String chatExchange = "chat-exchange";
    private static final String keywordExchange = "keyword-exchange";
    private final RabbitTemplate rabbitTemplate;

    public void rabbitChatProducer(ChatDTO chatDTO) {
        rabbitTemplate.convertAndSend(chatExchange, "messages." + String.valueOf(chatDTO.getRoom().getId()), chatDTO);
    }

    public void rabbitParentChildKeywordProducer(keywordPushDTO dto) {

        log.info("keyword queue ============= 1 =");
        rabbitTemplate.convertAndSend(keywordExchange, "keyword." + dto.getReceiver(), dto);
    }

    //알림 : 채팅, 자녀 등록 요청
}
