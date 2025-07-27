package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.dto.*;
import com.xymzsfxy.backend.entity.Articles;
import com.xymzsfxy.backend.entity.Users;
import com.xymzsfxy.backend.mapper.ArticleMapper;
import com.xymzsfxy.backend.mapper.CommentMapper;
import com.xymzsfxy.backend.mapper.UserMapper;
import com.xymzsfxy.backend.service.ArticleService;
import com.xymzsfxy.backend.service.ContentModerationService;
import com.xymzsfxy.backend.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j  // 添加这个注解
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    // 在 ArticleServiceImpl.java 中添加
    @Autowired
    private ContentModerationService moderationService;

    @Override
    public boolean articleCreate(ArticleCreateDTO articleCreateDTO, String accessToken) {
        try {
            // 解析token，获取用户ID
            Long userId = jwtUtils.getUserIdFromToken(accessToken);
            Users user = userMapper.selectDetailById(userId);

            // 先进行内容审核
            ModerationRequestDTO request = ModerationRequestDTO.builder()
                    .contentId(articleCreateDTO.getTitle() + "_" + System.currentTimeMillis())
                    .contentType("article")
                    .title(articleCreateDTO.getTitle())
                    .content(articleCreateDTO.getContent())
                    .authorId(userId)
                    .authorName(user.getUsername())
                    .category(articleCreateDTO.getCategory())
                    .moderationLevel("medium")
                    .source("web")
                    .build();

            ModerationResultDTO result = moderationService.moderateArticle(request);

            // 检查审核结果
            if ("REJECT".equals(result.getDecision())) {
                log.warn("文章审核被拒绝: {}", result.getAiReason());
                return false; // 审核不通过，直接返回失败
            }

            // 审核通过或需要人工审核，继续创建文章
            articleMapper.articleCreate(
                    userId,
                    articleCreateDTO.getTitle(),
                    articleCreateDTO.getContent(),
                    articleCreateDTO.getCategory()
            );

            return true;
        } catch (Exception e) {
            log.error("文章创建失败", e);
            return false;
        }
    }


    @Override
    public List<ArticleGetAllDTO> getAllArticles(Integer page, Integer pageSize, String category) {
        page = (page == null || page <= 0) ? 1 : page;
        pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        Integer offset = (page - 1) * pageSize;

        // 1. 查询当前页文章列表
        List<Articles> articlesAll = articleMapper.selectByPage(category, offset, pageSize);

        // 2. 提取所有用户ID（过滤空值）
        List<Long> userIds = articlesAll.stream()
                .map(Articles::getUserId)
                .filter(Objects::nonNull) // 过滤无用户ID的文章
                .collect(Collectors.toList());

        // 3. 批量查询用户信息并封装为 final Map
        final Map<Long, Users> userMap;
        if (!userIds.isEmpty()) {
            List<Users> users = userMapper.selectById(userIds);
            userMap = users.stream()
                    .collect(Collectors.toMap(
                            Users::getId,
                            user -> user,
                            (existing, replacement) -> existing
                    ));
        } else {
            userMap = Collections.emptyMap(); // 无用户ID时返回空Map
        }

        // 4. 转换为 DTO 列表（lambda 中引用 final Map）
        return articlesAll.stream()
                .map(article -> {
                    Users user = userMap.get(article.getUserId()); // 安全引用 final Map
                    return ArticleGetAllDTO.fromArticleEntity(article, user);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Long countByCategory(String category) {
        if(category.isEmpty() || category == null){
            Long aLong = articleMapper.countAll();
            return aLong;
        }
        Long aLong = articleMapper.countByCategory(category);
        return aLong;
    }

    @Override
    public Long countAll() {

        return articleMapper.countAll();
    }

    @Override
    public ArticleGetDetailDTO selectById(String id) {
        // 1. 查询文章基础信息
        Articles article = articleMapper.selectArticleById(id);
        if (article == null) return null;

        // 2. 查询作者信息
        Users user = userMapper.selectDetailById(article.getUserId());
        UserDetailInfoDTO userInfo = new UserDetailInfoDTO();
        if (user != null) {
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            userInfo.setAvatarUrl(user.getAvatarUrl());
            userInfo.setRole(user.getRole());
        }

        // 3. 查询评论信息
        List<CommentGetAllDTO> comments = commentMapper.selectCommentsByArticleId(id);

        // 4. 创建DTO
        ArticleGetDetailDTO detail = new ArticleGetDetailDTO();
        detail.setId(article.getId());
        detail.setTitle(article.getTitle());
        detail.setContent(article.getContent());
        detail.setCategory(article.getCategory());
        detail.setCreatedAt(article.getCreatedAt());
        detail.setViewCount(article.getViewCount());
        detail.setLikeCount(article.getLikeCount());
        detail.setCommentCount(article.getCommentCount());
        detail.setIsSticky(article.getIsSticky());
        detail.setUserInfo(userInfo);
        detail.setComments(comments);

        // 5. 增加文章浏览量
        articleMapper.incrementViewCount(id);

        return detail;
    }

    @Override
    public List<ArticleGetAllDTO> fuzzySearchArticles(String query, int offset, Integer size) {
        List<Articles> articles = articleMapper.fuzzyFindByTitle(query, offset, size);
        // 2. 提取所有用户ID（过滤空值）
        List<Long> userIds = articles.stream()
                .map(Articles::getUserId)
                .filter(Objects::nonNull) // 过滤无用户ID的文章
                .collect(Collectors.toList());

        // 3. 批量查询用户信息并封装为 final Map
        final Map<Long, Users> userMap;
        if (!userIds.isEmpty()) {
            List<Users> users = userMapper.selectById(userIds);
            userMap = users.stream()
                    .collect(Collectors.toMap(
                            Users::getId,
                            user -> user,
                            (existing, replacement) -> existing
                    ));
        } else {
            userMap = Collections.emptyMap(); // 无用户ID时返回空Map
        }

        // 假设有 ArticleGetAllDTO.fromArticleEntity 静态方法
        return articles.stream().map(article -> {
                    Users user = userMap.get(article.getUserId()); // 安全引用 final Map
                    return ArticleGetAllDTO.fromArticleEntity(article, user);
                })
                .collect(Collectors.toList());
    }
    @Override
    public Long fuzzyCountArticles(String query) {
        return articleMapper.fuzzyCountByTitle(query);
    }
}
