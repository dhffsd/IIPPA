package com.xymzsfxy.backend.controller.web;


import com.xymzsfxy.backend.dto.MessageEventDTO;
import com.xymzsfxy.backend.entity.UserMessage;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.xymzsfxy.backend.utils.JWTUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RestController
@RequestMapping("/web/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 获取用户消息
     */
    @GetMapping("info")
    public Result<Result.PageData<List<MessageEventDTO>>> getMessages(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @CookieValue(name = "access_token", required = false) String accessToken
    ) {
        if (accessToken == null) {
            return Result.badRequest("未登录");
        }
        List<MessageEventDTO> userMessages = messageService.getUserMessages(accessToken, type, page, size);
        // 这里可以加总数统计，假设你有总数方法
        int total = userMessages.size(); // 实际应查总数
        return Result.successWithPage(total, userMessages);
    }

    @GetMapping("unread-count")
    public Result<Integer> getUnreadCount(@CookieValue(name = "access_token", required = false) String accessToken) {
        if (accessToken == null) {
            return Result.success(0);
        }

        int count = messageService.getUnreadCount(accessToken);
        return Result.success(count);
    }

    @PostMapping("mark-read")
    public Result<Void> markRead(@RequestParam Long id, @CookieValue(name = "access_token", required = false) String accessToken) {
        if (accessToken == null) return Result.badRequest("未登录");
        messageService.markRead(accessToken, id);
        return Result.success();
    }

    @DeleteMapping("delete")
    public Result<Void> delete(@RequestParam Long id, @CookieValue(name = "access_token", required = false) String accessToken) {
        if (accessToken == null) return Result.badRequest("未登录");
        messageService.deleteMessage(accessToken, id);
        return Result.success();
    }
    
}

