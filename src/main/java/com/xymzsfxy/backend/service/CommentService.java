package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.dto.CommentCreateDTO;
import com.xymzsfxy.backend.dto.CommentListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    // 增加评论
    boolean commentCreate(CommentCreateDTO commentCreateDTO);

    // 获取评论列表
    List<CommentListDTO> getCommentList(String targetType, String targetId, Integer page, Integer size);

    // 获取评论总数
    Long getCommentTotal(String targetType, String targetId);
}
