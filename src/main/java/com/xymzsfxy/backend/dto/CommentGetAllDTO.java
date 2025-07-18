package com.xymzsfxy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentGetAllDTO {
    private String id;
    private String content;
    private UserInfoDTO userInfo;
    private Long likeCount;
    private Date createdAt;
    private List<AttachmentItem> attachments;


    public static class AttachmentItem {
        private String fileUrl;
        private String fileType;
        private String thumbnailUrl;
        private Integer displayOrder;

    }
}
