package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.entity.UserMessage;
import com.xymzsfxy.backend.mapper.MessageMapper;
import com.xymzsfxy.backend.service.MessageService;
import com.xymzsfxy.backend.utils.JWTUtils;
import com.xymzsfxy.backend.dto.MessageEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<MessageEventDTO> getUserMessages(String accessToken, String type, int page, int size) {
        Long userId = jwtUtils.getUserIdFromToken(accessToken);
        int offset = Math.max(0, (page) * size);
        String msgType = "REPLY".equalsIgnoreCase(type) ? "REPLY" : "SYSTEM";
        List<UserMessage> messages = messageMapper.selectByUserIdAndType(userId, msgType, offset, size);
        List<MessageEventDTO> dtoList = new java.util.ArrayList<>();
        for (UserMessage msg : messages) {
            dtoList.add(MessageEventDTO.fromEntity(msg));
        }
        return dtoList;
    }

    @Override
    public int getUnreadCount(String accessToken) {
        Long userId = jwtUtils.getUserIdFromToken(accessToken);
        return messageMapper.countUnread(userId);
    }

    @Override
    public void markRead(String accessToken, Long id) {
        Long userId = jwtUtils.getUserIdFromToken(accessToken);
        messageMapper.markRead(userId, id);
    }

    @Override
    public void deleteMessage(String accessToken, Long id) {
        Long userId = jwtUtils.getUserIdFromToken(accessToken);
        System.out.println(userId);
        messageMapper.deleteByIdAndReceiverId(id, userId);
    }
}
