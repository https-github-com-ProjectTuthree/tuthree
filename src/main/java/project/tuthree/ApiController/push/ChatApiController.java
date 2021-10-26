package project.tuthree.ApiController.push;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.ApiController.StatusCode;
import project.tuthree.configuration.Utils;
import project.tuthree.controller.JwtController;
import project.tuthree.controller.RedisTestService;
import project.tuthree.dto.ChatDTO;
import project.tuthree.dto.room.ChatroomDTO;
import project.tuthree.service.push.ChatService;
import project.tuthree.service.push.ChatService.chatRoomDTO;
import project.tuthree.service.push.ChatService.chatRoomListDTO;

import java.text.ParseException;
import java.util.List;

import static project.tuthree.configuration.Utils.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatApiController {

    private final RedisTestService redisTestService;
    private final JwtController jwtController;
    private final ChatService chatService;
/**
 * 채팅 전송
 * 채팅방 생성
 * 채팅 내역 조회
 * 채팅방 전체 조회
 * 읽지 않은 채팅 수 조회
 * */

    @PostMapping("/send")
    public NotExistDataResultResponse sendChat(@RequestBody ChatDTO chatDTO) {
        //valid
        chatService.sendChat(chatDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), "채팅 전송 완료");
    }

    /**
     * 문의하기를 눌렀을 경우 채팅방 생성 및 바로 채팅 하는 화면으로 넘어간다. 그리고 소켓 연결
     * 문의하기를 눌렀을 경우 이미 채팅방이 있으면 채팅하는 화면으로 넘어가고, 이전 채팅을 불러낸다.
     */

    /** 채팅방 찾기 - 채팅방 생성
     * 문의하기를 눌렀을 때, 선생님과 학생의 채팅방을 찾는다. 없으면 채팅방을 만들고 채팅방 번호를 리턴
     * 이후 채팅창에서 채팅하기를 하면 채팅방이 이미 만들어졌고, 번호도 아는 상태
     * */
    @PostMapping
    public NotExistDataResultResponse addChatRoom(@RequestBody ChatroomDTO chatroomDTO) {
        chatService.addChatRoomByIds(chatroomDTO.getUser1(), chatroomDTO.getUser2());
        //초기 채팅 보내기
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),"채팅방이 생성되었습니다.");
    }

    /** 채팅방 나가기 - 채팅방 삭제 */

    /** (채팅방 채팅 목록) 채팅 내역 불러오기*/
    @GetMapping("/{roomId}")
    public ExistDataSuccessResponse getChatByRoomId(@PathVariable("roomId") Long id) {
        List<chatRoomDTO> chatList = chatService.findChatListByRoomId(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                "채팅 목록을 불러왔습니다.", chatList);
    }

    /** (개인 채팅 목록) 참가한 채팅방 전체 조회 - 마지막으로 전송된 채팅도 같이 불러오기 & 읽지 않은 채팅 수
     *  + 안 읽은 채팅의 갯수
     * 파이어베이스 알림 쌓인 걸로 할 수 있나??
     * 알림 쌓인 걸 어떻게 해야할 지 모르겟다..
     * */
    @GetMapping("/list")
    public ExistDataSuccessResponse findChatRoomByOneId(@RequestHeader(AUTHORIZATION) String token) throws ParseException {
        String userId = jwtController.parseValueFromJwtToken(token, Utils.CLAIMUSERID);
        List<chatRoomListDTO> chatNotRead = chatService.findChatNotRead(userId);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),"채팅방을 조회했습니다.", chatNotRead);
    }

    /** fcm token 저장 api */
    @PostMapping("/fcm-save")
    public void saveFcmToken(@RequestBody TokenDTO tokenDTO) {
        redisTestService.setRedisStringValue(tokenDTO.getId(), tokenDTO.getToken());
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class TokenDTO {
        String id;
        String token;
    }


}
