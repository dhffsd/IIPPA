package com.xymzsfxy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleGetDetailDTO {
    private Long id;
    private String title;
    private String content;
    private String category;
    private UserDetailInfoDTO userInfo;
    private Date createdAt;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;
    private Integer isSticky;
    private List<CommentGetAllDTO> comments;
}
