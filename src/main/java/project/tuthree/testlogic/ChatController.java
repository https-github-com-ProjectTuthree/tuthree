package project.tuthree.testlogic;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Controller
@Log4j
public class ChatController extends TextWebSocketHandler {
    private static List<WebSocketSession> sessions;

    /** client 접속 - 세션 연결 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        super.afterConnectionEstablished(session);
        sessions.add(session);
        log.info("session 연결 : " + session);
    }
    /** client 접속 해제 - 세션 종료 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //super.afterConnectionClosed(session, status);
        sessions.remove(session);
        log.info("session 연결 해제 : " + session);
    }

    /** 채팅 메시지 다루기 */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //super.handleTextMessage(session, message);
        log.info("message : " + message.getPayload());

        for (WebSocketSession list : sessions) {
            list.sendMessage(message);

        }

    }
}
