package com.ohgiraffers.semiproject.messenger.controller;

import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import com.ohgiraffers.semiproject.messenger.model.dto.ChatDTO;
import com.ohgiraffers.semiproject.messenger.model.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final UserInfoService userInfoService;
    private final ChatService service;

    @Autowired
    public ChatController(UserInfoService userInfoService,ChatService service ){
        this.userInfoService = userInfoService;
        this.service = service;
    }

    @GetMapping("/{userId}/unread")
    public ResponseEntity<List<ChatDTO>> getUnreadMessages(@PathVariable Long userId) {

        List<ChatDTO> unreadList = service.getUnreadMessages(userId);

        return ResponseEntity.ok(unreadList);
    }

    @PostMapping("/{senderCode}/markAsRead")
    public ResponseEntity<Void> markMessagesAsRead(@PathVariable Long senderCode) {

        UserInfoResponse info = userInfoService.getUserInfo();

        String currentCode = info.getCode();

        // 서비스 호출하여 메시지를 읽음 처리
        boolean isUpdated = service.markMessagesAsRead(senderCode, currentCode);

        if (isUpdated) {
            // 성공적으로 업데이트된 경우 204 No Content 응답 반환
            return ResponseEntity.noContent().build();
        } else {
            // 업데이트 실패 시 404 Not Found 응답 반환
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/info")
    public UserInfoResponse getUserInfo() {
        UserInfoResponse userInfo = userInfoService.getUserInfo();  // 로그인한 사용자의 정보 가져오기
        return userInfo;  // 로그인한 사용자 정보를 응답으로 반환
    }

    // 채팅 기록 조회
    @GetMapping("/history/{senderCode}/{receiverCode}")
    public ResponseEntity<List<ChatDTO>> getChatHistory(@PathVariable String senderCode, @PathVariable String receiverCode) {

        List<ChatDTO> chatHistory = service.getChatHistory(senderCode, receiverCode);

        return ResponseEntity.ok(chatHistory); // 채팅 기록 반환
    }


    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatDTO sendMessage(ChatDTO chat) {

        // 클라이언트에서 전달된 senderCode를 직접 사용
        String senderCode = chat.getSenderCode();

        if (senderCode == null || senderCode.isEmpty()) {
            throw new IllegalArgumentException("Sender code is missing in the chat message.");
        }

        // 메시지 타임스탬프 추가
        chat.setTimestamp(new Timestamp(System.currentTimeMillis()));

        // 메시지 저장
        service.insertChat(chat, senderCode);

        // 메시지를 브로커로 전송

        return chat; // 메시지 브로커를 통해 전송
    }


}