package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.dto.MessageEventDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    List<MessageEventDTO> getUserMessages(String accessToken, String type, int page, int size);
    int getUnreadCount(String userId);
    void markRead(String accessToken, Long id);
    void deleteMessage(String accessToken, Long id);
}
