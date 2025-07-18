package com.xymzsfxy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentAttachmentDTO {
    /** 评论ID */
    private Long commentId;

    /** 文件访问URL（直接用于前端展示） */
    private String fileUrl;

    /** 文件类型（如："image/jpeg"、"video/mp4"） */
    private String fileType;

    /** 缩略图URL（仅图片类附件有） */
    private String thumbnailUrl;

    /** 显示顺序（从小到大排序） */
    private Integer displayOrder;
}
