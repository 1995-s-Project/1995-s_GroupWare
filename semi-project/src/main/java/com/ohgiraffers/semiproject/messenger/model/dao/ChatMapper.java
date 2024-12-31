package com.ohgiraffers.semiproject.messenger.model.dao;

import com.ohgiraffers.semiproject.messenger.model.dto.ChatDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {


    List<ChatDTO> findChatHistory(@Param("senderCode") String senderCode, @Param("receiverCode") String receiverCode);

    void save(ChatDTO chat, String senderCode);

    // 안 읽은 메세지 가져오기
    List<ChatDTO> getUnreadMessages(Long userId);

    // 읽은 표시 업데이트
    boolean markMessagesAsRead(Long userId, String currentCode);
}