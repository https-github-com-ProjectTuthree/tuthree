package project.tuthree.ApiController.push;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.configuration.Utils;
import project.tuthree.controller.JwtController;
import project.tuthree.controller.RedisTestService;
import project.tuthree.dto.ChatDTO;
import project.tuthree.service.push.ChatService;
import project.tuthree.service.push.ChatService.chatRoomDTO;
import project.tuthree.service.push.ChatService.chatRoomListDTO;
import project.tuthree.service.push.ChatService.chatRoomNameDTO;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

import static project.tuthree.configuration.Utils.AUTHORIZATION;
import static project.tuthree.configuration.Utils.CLAIMUSERID;

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
    @ResponseStatus(HttpStatus.CREATED)
    public NotExistDataResultResponse sendChat(@RequestBody @Valid ChatDTO chatDTO) {
        //valid
        chatService.sendChat(chatDTO);
        return new NotExistDataResultResponse(HttpStatus.CREATED.value(), "채팅 전송 완료");
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
    @ResponseStatus(HttpStatus.CREATED)
    public NotExistDataResultResponse addChatRoom(@RequestBody @Valid chatRoomNameDTO dto) throws Exception {
        try{
            chatService.addChatRoomByIds(dto);
            return new NotExistDataResultResponse(HttpStatus.CREATED.value(), "채팅방이 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("잘못된 요청입니다");
        }
    }

    /** 채팅방 나가기 - 채팅방 삭제 */

    /** (채팅방 채팅 목록) 채팅 내역 불러오기*/
    @GetMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public ExistDataSuccessResponse getChatByRoomId(@RequestHeader(AUTHORIZATION) String token, @PathVariable("roomId") Long id) {
        String userId = jwtController.parseValueFromJwtToken(token, CLAIMUSERID);
        chatRoomDTO chatListByRoomId = chatService.findChatListByRoomId(id, userId);
        return new ExistDataSuccessResponse(HttpStatus.OK.value(),
                "채팅 목록을 불러왔습니다.", chatListByRoomId);
    }

    /** (개인 채팅 목록) 참가한 채팅방 전체 조회 - 마지막으로 전송된 채팅도 같이 불러오기 & 읽지 않은 채팅 수
     *  + 안 읽은 채팅의 갯수
     * 파이어베이스 알림 쌓인 걸로 할 수 있나??
     * 알림 쌓인 걸 어떻게 해야할 지 모르겟다..
     * */
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ExistDataSuccessResponse findChatRoomByOneId(@RequestHeader(AUTHORIZATION) String token) throws ParseException {
        String userId = jwtController.parseValueFromJwtToken(token, Utils.CLAIMUSERID);
        List<chatRoomListDTO> chatNotRead = chatService.findChatNotRead(userId);
        return new ExistDataSuccessResponse(HttpStatus.OK.value(), "채팅방을 조회했습니다.", chatNotRead);
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
