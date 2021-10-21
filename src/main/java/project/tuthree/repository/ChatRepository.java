package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.tuthree.domain.room.ChatRoom;
import project.tuthree.domain.room.QChatRoom;

import javax.persistence.EntityManager;

import static project.tuthree.domain.room.QChatRoom.chatRoom;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ChatRepository {

    /**
     * charoom : sender, reciver 아이디로 chatroom 찾기
     * chatlog : roomid, reciever, true로 room 전부 찾기
     *          roomid, true, reciever로 카운트 전부
     *          reciver, true로 카운트 전부
     * */

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /** 채팅방 개설 */
    public Long makeChatRoom(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom.getId();
    }

    /** 채팅방 찾기 */
    public Long findChatRoom(String user1, String user2) {
        try {
            return jpaQueryFactory.select(chatRoom.id).from(chatRoom)
                    .where((chatRoom.user1.eq(user1).and(chatRoom.user2.eq(user2)))
                                    .or(chatRoom.user2.eq(user1)).and(chatRoom.user1.eq(user2))
                    )
                    .fetchOne();
        } catch (Exception e) {
            return -1L;
        }
    }
}
