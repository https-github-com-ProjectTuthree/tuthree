package project.tuthree.testlogic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Repository
@RequiredArgsConstructor
@EnableWebSocket
public class WebsocketRepository implements WebSocketConfigurer {

    private final ChatController chatController;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatController, "ws/chat").setAllowedOrigins("*");
    }
}
