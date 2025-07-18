package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.dto.CommentCreateDTO;
import com.xymzsfxy.backend.dto.CommentListDTO;
import com.xymzsfxy.backend.entity.Comments;
import com.xymzsfxy.backend.entity.commentAttachments;
import com.xymzsfxy.backend.mapper.CommentMapper;
import com.xymzsfxy.backend.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;



    @Override
    public boolean commentCreate(CommentCreateDTO commentCreateDTO) {
        try {

            // 1. 创建评论主体
            Comments comment = new Comments();
            BeanUtils.copyProperties(commentCreateDTO, comment);
            comment.setRootId(0L); // 初始化根评论ID

            commentMapper.insertComment(comment);

            List<CommentCreateDTO.AttachmentItem> attachments = commentCreateDTO.getAttachments();

            if(attachments != null && !attachments.isEmpty()){
                for (int i = 0; i < attachments.size(); i++) {
                    CommentCreateDTO.AttachmentItem Item = attachments.get(i);
                    commentAttachments commentAttachments = new commentAttachments();
                    commentAttachments.setCommentId(comment.getId());
                    commentAttachments.setFileUrl(Item.getFileUrl());
                    commentAttachments.setFileType(Item.getFileType());
                    commentAttachments.setThumbnailUrl(Item.getThumbnailUrl());
                    commentAttachments.setDisplayOrder(i+1);
                    commentMapper.insertCommentAttachment(commentAttachments);
                }
            }

            return true;

        }catch (Exception e){
            System.out.println(e);
            return false;
        }

    }

    @Override
    public List<CommentListDTO> getCommentList(String targetType, String targetId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        return commentMapper.selectCommentList(targetType, targetId, offset, size);
    }

    @Override
    public Long getCommentTotal(String targetType, String targetId) {
        return commentMapper.selectCommentTotal(targetType, targetId);
    }
}
