package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.dto.CommentCreateDTO;
import com.xymzsfxy.backend.dto.CommentListDTO;
import com.xymzsfxy.backend.dto.ModerationRequestDTO;
import com.xymzsfxy.backend.dto.ModerationResultDTO;
import com.xymzsfxy.backend.entity.Comments;
import com.xymzsfxy.backend.entity.commentAttachments;
import com.xymzsfxy.backend.mapper.CommentMapper;
import com.xymzsfxy.backend.service.CommentService;
import com.xymzsfxy.backend.service.ContentModerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
@Slf4j  // 添加这个注解
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;



    // 在 CommentServiceImpl.java 中添加
    @Autowired
    private ContentModerationService moderationService;

    @Override
    public boolean commentCreate(CommentCreateDTO commentCreateDTO) {
        try {
            // 先进行内容审核
            ModerationRequestDTO request = ModerationRequestDTO.builder()
                    .contentId(commentCreateDTO.getTargetId() + "_" + System.currentTimeMillis())
                    .contentType("comment")
                    .content(commentCreateDTO.getContent())
                    .authorId(commentCreateDTO.getUserId())
                    .moderationLevel("low")
                    .source("web")
                    .build();

            ModerationResultDTO result = moderationService.moderateComment(request);

            // 检查审核结果
            if ("REJECT".equals(result.getDecision())) {
                log.warn("评论审核被拒绝: {}", result.getAiReason());
                return false; // 审核不通过，直接返回失败
            }

            // 审核通过或需要人工审核，继续创建评论
            Comments comment = new Comments();
            BeanUtils.copyProperties(commentCreateDTO, comment);
            comment.setRootId(0L);
            commentMapper.insertComment(comment);

            // 处理附件...
            List<CommentCreateDTO.AttachmentItem> attachments = commentCreateDTO.getAttachments();
            if(attachments != null && !attachments.isEmpty()){
                for (int i = 0; i < attachments.size(); i++) {
                    CommentCreateDTO.AttachmentItem Item = attachments.get(i);
                    commentAttachments commentAttachments = new commentAttachments();
                    commentAttachments.setCommentId(comment.getId());
                    commentAttachments.setFileUrl(Item.getFileUrl());
                    
                    // 限制 file_type 长度，避免数据库字段截断错误
                    String fileType = Item.getFileType();
                    if (fileType != null && fileType.length() > 20) {
                        fileType = fileType.substring(0, 20);
                        log.warn("文件类型过长，已截断: {} -> {}", Item.getFileType(), fileType);
                    }
                    commentAttachments.setFileType(fileType);
                    
                    commentAttachments.setThumbnailUrl(Item.getThumbnailUrl());
                    commentAttachments.setDisplayOrder(i+1);
                    commentMapper.insertCommentAttachment(commentAttachments);
                }
            }

            return true;
        } catch (Exception e) {
            log.error("评论创建失败", e);
            return false;
        }
    }

    @Override
    public List<CommentListDTO> getCommentList(String targetType, String targetId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<CommentListDTO> comments = commentMapper.selectCommentList(targetType, targetId, offset, size);
        
        // 为每个评论加载附件数据
        for (CommentListDTO comment : comments) {
            try {
                Long commentId = Long.parseLong(comment.getId());
                List<com.xymzsfxy.backend.dto.CommentAttachmentDTO> attachments = 
                    commentMapper.selectAttachmentsByCommentId(commentId);
                comment.setAttachments(attachments);
            } catch (NumberFormatException e) {
                log.warn("评论ID格式错误: {}", comment.getId());
                comment.setAttachments(new ArrayList<>());
            } catch (Exception e) {
                log.error("加载评论附件失败: commentId={}", comment.getId(), e);
                comment.setAttachments(new ArrayList<>());
            }
        }
        
        return comments;
    }

    @Override
    public Long getCommentTotal(String targetType, String targetId) {
        return commentMapper.selectCommentTotal(targetType, targetId);
    }
}
