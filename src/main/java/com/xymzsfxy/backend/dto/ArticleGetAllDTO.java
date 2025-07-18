package com.xymzsfxy.backend.dto;

import com.xymzsfxy.backend.entity.Articles;
import com.xymzsfxy.backend.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleGetAllDTO {

    /*
    * 文章基本字段
    *  */
    private Long id;
    private String title;
    private String content;
    private String category;
    private Integer isSticky;
    private Integer isFeatured;
    private Long viewCount;
    private Long commentCount;
    private Long likeCount;
    private GetUserInfoDTO getUserInfoDTO;
    private Date createdAt;

    public static ArticleGetAllDTO fromArticleEntity(Articles articles, Users users){
        return ArticleGetAllDTO.builder()
                .id(articles.getId())
                .title(articles.getTitle())
                .content(articles.getContent())
                .category(articles.getCategory())
                .isSticky(articles.getIsSticky())
                .isFeatured(articles.getIsFeatured())
                .viewCount(articles.getViewCount())
                .commentCount(articles.getCommentCount())
                .likeCount(articles.getLikeCount())
                .getUserInfoDTO(GetUserInfoDTO.builder()
                        .id(users.getId())
                        .username(users.getUsername())
                        .avatarUrl(users.getAvatarUrl())
                        .role(users.getRole())
                        .badges(users.getBadges())
                        .build())
                .createdAt(articles.getCreatedAt())
                .build();
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUserInfoDTO {
        private Long id;
        private String username;
        private String avatarUrl;
        private String role;
        private String badges;
    }


}
