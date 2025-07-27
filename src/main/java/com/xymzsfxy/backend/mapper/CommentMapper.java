package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.dto.CommentGetAllDTO;
import com.xymzsfxy.backend.dto.CommentListDTO;
import com.xymzsfxy.backend.entity.Comments;
import com.xymzsfxy.backend.entity.commentAttachments;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comments(user_id, target_type, target_id, content, created_at, updated_at)" +
            "values(#{userId}, #{targetType}, #{targetId}, #{content}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertComment(Comments comments);

    @Insert("insert into comment_attachments(comment_id, file_url, file_type, thumbnail_url, display_order, created_at)" +
            "values(#{commentId}, #{fileUrl}, #{fileType}, #{thumbnailUrl}, #{displayOrder}, NOW())")
    void insertCommentAttachment(commentAttachments commentAttachments);

    @Select("SELECT c.*, u.username, u.avatar_url, u.role " +
            "FROM comments c " +
            "LEFT JOIN users u ON c.user_id = u.id " +
            "WHERE c.target_id = #{articleId} AND c.target_type = 'article'")
    List<CommentGetAllDTO> selectCommentsByArticleId(String articleId);

    @Select("SELECT \n" +
            "            c.id AS id,\n" +
            "            c.content AS content,\n" +
            "            c.created_at AS createTime,\n" +
            "            c.like_count AS likeCount,\n" +
            "            u.username AS username,\n" +
            "            u.role AS role,\n" +
            "            u.avatar_url AS avatarUrl\n" +
            "        FROM \n" +
            "            comments c\n" +
            "        LEFT JOIN \n" +
            "            users u ON c.user_id = u.id\n" +
            "        WHERE \n" +
            "            c.target_type = #{targetType}\n" +
            "            AND c.target_id = #{targetId}\n" +
            "        ORDER BY \n" +
            "            c.created_at DESC\n" +
            "        LIMIT #{offset}, #{size}")
    List<CommentListDTO> selectCommentList(String targetType, String targetId, int offset, Integer size);

    @Select("SELECT COUNT(*) \n" +
            "        FROM comments \n" +
            "        WHERE \n" +
            "            target_type = #{targetType}\n" +
            "            AND target_id = #{targetId}\n" )
    Long selectCommentTotal(String targetType, String targetId);

    @Select("SELECT \n" +
            "            ca.comment_id AS commentId,\n" +
            "            ca.file_url AS fileUrl,\n" +
            "            ca.file_type AS fileType,\n" +
            "            ca.thumbnail_url AS thumbnailUrl,\n" +
            "            ca.display_order AS displayOrder\n" +
            "        FROM \n" +
            "            comment_attachments ca\n" +
            "        WHERE \n" +
            "            ca.comment_id = #{commentId}\n" +
            "        ORDER BY \n" +
            "            ca.display_order ASC")
    List<com.xymzsfxy.backend.dto.CommentAttachmentDTO> selectAttachmentsByCommentId(Long commentId);
}
