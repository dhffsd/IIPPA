package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.dto.ArticleCreateDTO;
import com.xymzsfxy.backend.dto.ArticleGetAllDTO;
import com.xymzsfxy.backend.dto.ArticleGetDetailDTO;
import com.xymzsfxy.backend.entity.Articles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    // 创建文章
    boolean articleCreate(ArticleCreateDTO articleCreateDTO, String accessToken);
    // 获取所有文章
    List<ArticleGetAllDTO> getAllArticles(Integer page, Integer pageSize, String category);

    // 返回文章记录数
    Long countByCategory(String category);

    // 返回所有文章数量
    Long countAll();

    // 根据ID返回文章
    ArticleGetDetailDTO selectById(String id);

    List<ArticleGetAllDTO> fuzzySearchArticles(String query, int offset, Integer size);
    Long fuzzyCountArticles(String query);
}
