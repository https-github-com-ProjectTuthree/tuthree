package project.tuthree.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Chat;
import project.tuthree.domain.QChat;
import project.tuthree.domain.room.ChatRoom;
import project.tuthree.service.push.ChatService;
import project.tuthree.service.push.ChatService.chatRoomDTO;
import project.tuthree.service.push.ChatService.chatRoomDTO.chatListDTO;
import project.tuthree.service.push.ChatService.chatRoomListDTO;

import javax.persistence.EntityManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static project.tuthree.domain.QChat.chat;
import static project.tuthree.domain.room.QChatRoom.chatRoom;
import static project.tuthree.exception.ExceptionSupplierImpl.wrap;


@Slf4j
@Repository
@Transactional
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
    public ChatRoom makeChatRoom(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom;
    }

    public ChatRoom findChatRoomByRoomId(Long id) {
        return em.find(ChatRoom.class, id);
    }

    /** 채팅방 찾기 */
    public Long findChatRoomByIds(String user1, String user2) {
        try {
            Long id = jpaQueryFactory.select(chatRoom.id).from(chatRoom)
                    .where((chatRoom.user1.eq(user1).and(chatRoom.user2.eq(user2)))
                            .or((chatRoom.user2.eq(user1)).and(chatRoom.user1.eq(user2)))
                    )
                    .fetchOne();
            return (id == null) ? -1L : id;
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

    public List<ChatRoom> findChatRoomById(String id) {

        return jpaQueryFactory.selectFrom(chatRoom)
                .where(chatRoom.user1.eq(id).or(chatRoom.user2.eq(id)))
                .fetch();

    }

    /** 한명이 속한 채팅방 찾아서 마지막 채팅과 함께 반환 */
    public List<chatRoomListDTO> findChatRoomWLogById(String id) {

        QChat m = chat;
        QChat n = new QChat("n");
        List<Chat> fetch = jpaQueryFactory.selectFrom(m)
                .where(m.chatAt.eq(jpaQueryFactory.select(n.chatAt.max())
                        .from(n)
                        .where((n.room.user1.eq(id).or(n.room.user2.eq(id)))
                                .and(n.room.eq(m.room)))
                        .groupBy(n.room))
                )
                .fetch();

        return fetch.stream()
                .map(t -> wrap(() -> new chatRoomListDTO(t.getRoom().getId(), new chatRoomListDTO.chatListDTO(t.getUserId(), t.getName(), t.getContent(), userFileRepository.unixToDate(t.getChatAt())))))
                .collect(Collectors.toList());
    }

    /** 읽지 않은 채팅 수 */
    public Map<Long, Long> findChatNotReadByUserId(String userId) {
        Map<Long, Long> map = new HashMap<>();
        List<Tuple> fetch = jpaQueryFactory.select(chat.room.id, chat.count()).from(chat)
                .where((chat.room.user1.eq(userId).or(chat.room.user2.eq(userId)))
                        .and(chat.userId.equalsIgnoreCase(userId))
                        .and(chat.read.eq(false)))
                .groupBy(chat.room).fetch();

        fetch.stream().forEach(m -> map.put(m.get(0, Long.class), m.get(1, Long.class)));
        return map;
    }


}
