package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.ContentModeration;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContentModerationMapper {
    @Insert("INSERT INTO content_moderation (content_id, content_type, title, content, author_id, " +
            "author_name, category, decision, confidence, violations, ai_reason, status, " +
            "moderation_level, created_at) VALUES (#{contentId}, #{contentType}, #{title}, " +
            "#{content}, #{authorId}, #{authorName}, #{category}, #{decision}, #{confidence}, " +
            "#{violations}, #{aiReason}, #{status}, #{moderationLevel}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ContentModeration moderation);

    @Select("SELECT * FROM content_moderation WHERE content_id = #{contentId} AND content_type = #{contentType} ORDER BY created_at DESC")
    List<ContentModeration> findByContentIdAndType(@Param("contentId") String contentId, @Param("contentType") String contentType);

    @Select("SELECT * FROM content_moderation WHERE id = #{id}")
    ContentModeration findById(@Param("id") Long id);

    @Update("UPDATE content_moderation SET decision = #{decision}, manual_reason = #{manualReason}, " +
            "reviewer_id = #{reviewerId}, status = #{status}, reviewed_at = #{reviewedAt} WHERE id = #{id}")
    void update(ContentModeration moderation);

    @Select("SELECT * FROM content_moderation WHERE status = 'PENDING' ORDER BY created_at ASC LIMIT #{limit}")
    List<ContentModeration> findPendingModerations(@Param("limit") int limit);

}
