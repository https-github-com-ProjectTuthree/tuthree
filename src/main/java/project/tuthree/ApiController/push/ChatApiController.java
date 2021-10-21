package project.tuthree.ApiController;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import project.tuthree.controller.MessageController;
import project.tuthree.controller.MessageSender;
import project.tuthree.controller.RabbitService;
import project.tuthree.dto.ChatDTO;
import project.tuthree.service.push.ChatService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatApiController {

    private final RabbitService rabbitService;
//    private final SimpMessageSendingOperations msgTemplate;
    private final ChatService chatService;
    private final MessageController controller;
    private final MessageSender sender;


//    @ApiOperation(value = "채팅 전송", notes = "채팅 전송")
//    @MessageMapping("/chat/msg")
//    public void returning(@RequestBody ChatDTO chatDTO) throws IOException {
//        msgTemplate.convertAndSend("/exchange/chat-exchange/msg." + chatDTO.getRoom().getId(), new ChatDTO(chatDTO));
//        rabbitService.rabbitChatProducer(chatDTO);
//    }

    /** 채팅방 개설 - 사용자 아이디 2개, 이미 채팅방이 있는 경우 바로 redirect */
    @GetMapping("/chatRoom")
    public void makeChatRoomByIds(@RequestParam("user1") String user1,
                                  @RequestParam("user2") String user2, final HttpServletResponse response) throws IOException {
//        Long roomId = chatService.makeChatRoomByIds(user1, user2);
        //uri redirect
        response.sendRedirect("/chat/{roomId}");
    }

    /** 채팅방 입성 - 이전 채팅 불러오기 (파라미터 받아서 새로 만든 방이면 불러오지 않기) */
    @GetMapping("/chat/{room}")
    public void startChat(@PathVariable("room") Long id){

    }

    @PostMapping("/chat")
    public boolean chatController(@RequestParam("room") Long id, final @RequestBody ChatDTO chatDTO) throws InterruptedException {
        controller.sendMessage(chatDTO);
        sender.send(id, chatDTO.getContent());
        return true;
    }
/**
 * 채팅 전송
 * 채팅방 생성
 * 채팅 내역 조회
 * 채팅방 전체 조회
 * 읽지 않은 채팅 수 조회
 *
 *
 * */
//
//@ApiOperation(value = "채팅 전송", notes = "채팅 전송")
//@MessageMapping("/chat/msg")
//public void send(@RequestBody @Valid ChatRequestDto chatRequestDto) {
//        msgTemplate.convertAndSend("/exchange/chat-exchange/msg." + chatRequestDto.getRoomId(), new ChatLogResponseDto(chatRequestDto));
//        rabbitService.rabbitChatProducer(chatRequestDto);
//        }
//
//@ApiOperation(value = "채팅방 생성", notes = "채팅방 생성")
//@PostMapping("/chat")
//public ResponseEntity<Long> addChatRoom(@RequestBody ChatRequestDto chatRequestDto) {
//        return new ResponseEntity<>(chatService.addChatRoom(chatRequestDto), HttpStatus.CREATED);
//        }
//
//@ApiOperation(value = "채팅내역 조회", notes = "채팅방의 채팅 내역 조회")
//@ApiImplicitParams
//        ({
//                @ApiImplicitParam(name = "roomId", dataType = "Long", value = "채팅방 PK", required = true),
//                @ApiImplicitParam(name = "nickname", dataType = "String", value = "본인 닉네임", required = true)
//        })
//@GetMapping("/chat/{roomId}")
//public ResponseEntity<ChatRoomResponseDto> getChatLogByRoomId(@PathVariable Long roomId,
//@RequestParam String nickname) {
//        return new ResponseEntity<>(chatService.findAllChatLogByRoomId(roomId, nickname), HttpStatus.OK);
//        }
//
//@ApiOperation(value = "채팅방 전체 조회", notes = "채팅방 전체 조회")
//@ApiImplicitParam(name = "nickname", dataType = "String", value = "본인 닉네임", required = true)
//@GetMapping("/chat")
//public ResponseEntity<List<ChatRoomListResponseDto>> getChatRooms(@RequestParam String nickname) {
//        return new ResponseEntity<>(chatService.findAllChatRoomByNickname(nickname), HttpStatus.OK);
//        }
//
//@ApiOperation(value = "읽지 않은 채팅 수 조회", notes = "읽지 않은 채팅 수 조회")
//@ApiImplicitParam(name = "nickname", dataType = "String", value = "본인 닉네임", required = true)
//@GetMapping("/chat/read")
//public ResponseEntity<Integer> getChatLogNotRead(@RequestParam String nickname) {
//        return new ResponseEntity<>(chatService.countAllNotReadChatLog(nickname), HttpStatus.OK);
//        }

}
