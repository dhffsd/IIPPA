package com.xymzsfxy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListDTO {
    private String id;          // 评论ID
    private String content;     // 评论内容
    private Date createTime;    // 评论时间
    private Integer likeCount;  // 点赞数
    private String username;    // 用户名（关联用户表）
    private String role;
    private String avatarUrl;   // 用户头像（关联用户表）
    private List<CommentAttachmentDTO> attachments; // 评论附件列表
}
