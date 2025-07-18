package com.xymzsfxy.backend.dto;

//import com.xymzsfxy.backend.entity.Articles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateDTO {
    private String title;
    private String content;
    private String category;

//    public static ArticleCreateDTO fromEntity(Articles articles){
//        return ArticleCreateDTO.builder()
//                .title(articles.getTitle())
//                .content(articles.getContent())
//                .category(articles.getCategory())
//                .isSticky(articles.getIsSticky())
//                .build();
//    }


}
