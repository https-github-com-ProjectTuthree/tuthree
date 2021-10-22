package project.tuthree.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.tuthree.domain.Chat;
import project.tuthree.domain.room.ChatRoom;
import project.tuthree.service.push.ChatService.chatListDTO;

import javax.persistence.EntityManager;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import static project.tuthree.domain.QChat.chat;
import static project.tuthree.domain.room.QChatRoom.chatRoom;
import static project.tuthree.exception.ExceptionSupplierImpl.wrap;


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
    private final UserFileRepository userFileRepository;

    /** 채팅방 개설 */
    public Long makeChatRoom(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom.getId();
    }

    /** 채팅방 찾기 */
    public Long findChatRoomByIds(String user1, String user2) {
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

    /** 채팅 전송 - 채팅 로그 남기기 */
    public Long saveChatLog(Chat chat) {
        em.persist(chat);
        return chat.getId();
    }

    /** 이전 채팅 목록 불러오기 */
    public List<Chat> findChatListByRoomId(Long id) {
        return jpaQueryFactory.selectFrom(chat)
                .where(chat.room.id.eq(id))
                .fetch();
    }

    /** 한명이 속한 채팅방 찾기 */
    public List<chatListDTO> findChatRoomById(String id) throws ParseException {
        List<Chat> fetch = jpaQueryFactory.selectFrom(chat)
                .where(chat.chatAt.eq(chat.chatAt.max()))
                .groupBy(JPAExpressions.select(chatRoom.id).from(chatRoom)
                        .where(chatRoom.user1.eq(id).or(chatRoom.user2.eq(id))))
                .fetch();
        return fetch.stream()
                .map(m -> wrap(() -> new chatListDTO(m.getRoom().getId(), m.getRoom().getUser1(),
                                m.getRoom().getUser2(), m.getContent(), userFileRepository.unixToDate(m.getChatAt()))))
                .collect(Collectors.toList());
    }

    /** 읽지 않은 채팅 수 */
    public Long findChatNotReadByRoomId(Long id) {
        return jpaQueryFactory.selectFrom(chat)
                .where(chat.room.id.eq(id))
                .fetchCount();
    }

}
