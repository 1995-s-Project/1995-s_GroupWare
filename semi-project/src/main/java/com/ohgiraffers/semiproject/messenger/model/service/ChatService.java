package com.ohgiraffers.semiproject.messenger.model.service;

import com.ohgiraffers.semiproject.messenger.model.dao.ChatMapper;
import com.ohgiraffers.semiproject.messenger.model.dto.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatService {

    private final ChatMapper mapper;

    @Autowired
    public ChatService(ChatMapper mapper) {
        this.mapper = mapper;
    }

    public List<ChatDTO> getChatHistory(String senderCode, String receiverCode) {
        return mapper.findChatHistory(senderCode, receiverCode); // DB에서 사용자 간 채팅 기록 조회
    }

    public void insertChat(ChatDTO chat, String senderCode) {
        mapper.save(chat, senderCode);
    }

    // 안 읽은 메세지 가져오기
    public List<ChatDTO> getUnreadMessages(Long userId) {
        return mapper.getUnreadMessages(userId);
    }

    // 읽은 값으로 업데이트 하기
    @Transactional
    public boolean markMessagesAsRead(Long userId, String currentCode) {
        return mapper.markMessagesAsRead(userId, currentCode);
    }
}
