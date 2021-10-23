//package project.tuthree.ApiController.push;
//
//import lombok.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.web.bind.annotation.*;
//import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
//import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
//import project.tuthree.ApiController.StatusCode;
//import project.tuthree.dto.ChatDTO;
//import project.tuthree.repository.ChatRepository;
//import project.tuthree.service.push.ChatService;
//import project.tuthree.service.push.ChatService.chatListDTO;
//import project.tuthree.service.push.RabbitService;
//
//import java.text.ParseException;
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class ChatApiController {
//
//    private final SimpMessageSendingOperations msgTemplate;
//    private final RabbitService rabbitService;
//    private final ChatService chatService;
//    private final ChatRepository chatRepository;
///**
// * 채팅 전송
// * 채팅방 생성
// * 채팅 내역 조회
// * 채팅방 전체 조회
// * 읽지 않은 채팅 수 조회
// * */
//
////??messagemapping??
//    @PostMapping("/chat/send")
//    public NotExistDataResultResponse sendChat(@RequestBody ChatDTO chatDTO) {
//        //valid
//        //이건 솔직히 뭔지 잘 모르겠다...
//        msgTemplate.convertAndSend("/exchange/chat-exchange/msg." + chatDTO.getRoom().getId(), chatDTO);
//        //큐로 보내기
//        rabbitService.rabbitChatProducer(chatDTO);
//        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),
//                "채팅 전송 완료");
//    }
//
//    /** 채팅방 생성 */
//    @PostMapping("/chat")
//    public NotExistDataResultResponse addChatRoom(@RequestBody ChatDTO chatDTO) {
//        chatService.addChatRoomByIds("", "");//아이디를 두개를 넘겨야 되는데,..흠..
//        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),
//                "채팅방에 생성되었습니다.");
//    }
//
//    /** 채팅방 나가기 - 채팅방 삭제 */
//
//    /** (채팅방 채팅 목록) 채팅 내역 불러오기 */
//    @GetMapping("/chat/{roomId}")
//    public ExistDataSuccessResponse getChatByRoomId(@RequestParam("roomId") Long id) {
//        List<ChatDTO> chatList = chatService.findChatListByRoomId(id);
//        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
//                "채팅 목록을 불러왔습니다.", chatList);
//    }
//
//    /** (개인 채팅 목록) 참가한 채팅방 전체 조회 - 마지막으로 전송된 채팅도 같이 불러오기 & 읽지 않은 채팅 수*/
//    @GetMapping("/chat/list")
//    public ExistDataSuccessResponse findChatRoomByOneId(@RequestParam("id") String id) throws ParseException {
//        List<chatListDTO> chatRoomById = chatRepository.findChatRoomById(id);
//        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
//                "채팅방을 조회했습니다.", chatRoomById);
//    }
//
//}
