package project.tuthree.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Chat;
import project.tuthree.domain.QChat;
import project.tuthree.domain.Status;
import project.tuthree.domain.room.ChatRoom;
import project.tuthree.domain.user.StudentRepository;
import project.tuthree.domain.user.TeacherRepository;
import project.tuthree.domain.user.UserRepository;
import project.tuthree.service.push.ChatService.chatRoomListDTO;

import javax.persistence.EntityManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static project.tuthree.domain.QChat.chat;
import static project.tuthree.domain.room.QChatRoom.chatRoom;


@Slf4j
@Repository
@Transactional(rollbackFor = Exception.class)
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
    private final UserEntityRepository userEntityRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;

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
    public List<Chat> findChatListByRoomId(Long id, String userId) {
        List<Chat> fetch = jpaQueryFactory.selectFrom(chat)
                .where(chat.room.id.eq(id))
                .orderBy(chat.chatAt.asc())
                .fetch();
        for (Chat c : fetch) {
            if(c.getUserId().equals(userId)) c.updateRead();
        }
        return fetch;
    }

    public List<ChatRoom> findChatRoomById(String id) {
        return jpaQueryFactory.selectFrom(chatRoom)
                .where(chatRoom.user1.eq(id).or(chatRoom.user2.eq(id)))
                .fetch();
    }

    /** 한명이 속한 채팅방 찾아서 마지막 채팅과 함께 반환 */
    public List<chatRoomListDTO> findChatRoomWLogById(String id) throws ParseException {
        List<chatRoomListDTO> list = new ArrayList<>();
        QChat m = chat;
        QChat n = new QChat("n");
        List<Chat> fetch = jpaQueryFactory.selectFrom(m).distinct()
                .where(m.chatAt.eq(jpaQueryFactory.select(n.chatAt.max())
                        .from(n)
                        .where((n.room.user1.eq(id).or(n.room.user2.eq(id)))
                                .and(n.room.eq(m.room)))
                        .groupBy(n.room))
                )
                .orderBy(m.chatAt.desc())
                .fetch();

        for (Chat c : fetch) {
            String userId = c.getRoom().getUser1();
            String userName = "";
            Status userReg = Status.CLOSE;

            if(id.equals(c.getRoom().getUser1())){
                userId = c.getRoom().getUser2();
            }

            if(teacherRepository.existsById(userId)){
                Tuple tuple = userEntityRepository.findTeacherNameByIdnReg(userId);
                userReg = tuple.get(0, Status.class);
                userName = tuple.get(1, String.class);
            }else if(studentRepository.existsById(userId)){
                Tuple tuple = userEntityRepository.findStudentNameByIdnReg(userId);;
                userReg = tuple.get(0, Status.class);
                userName = tuple.get(1, String.class);
            }else if(userRepository.existsById(userId)){
                userReg = Status.CLOSE;
                userName = userEntityRepository.findParentNameByIdnReg(userId);
            }else if(adminRepository.existById(userId)){
                userName = "관리자";
            }
            chatRoomListDTO chatRoomListDTO = new chatRoomListDTO(c.getRoom().getId(), new chatRoomListDTO.chatListDTO(userId, userName, userReg, c.getContent(), userFileRepository.unixToTimestamp(c.getChatAt())));
            list.add(chatRoomListDTO);
        }
        return list;
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
