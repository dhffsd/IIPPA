package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("SELECT * FROM user_message WHERE receiver_id = #{userId} AND type = #{type} ORDER BY created_at DESC LIMIT #{size} OFFSET #{offset}")
    List<UserMessage> selectByUserIdAndType(@Param("userId") Long userId, @Param("type") String type, @Param("offset") Integer offset, @Param("size") int size);

    @Select("UPDATE user_message SET is_read = 1 WHERE id = #{id} AND receiver_id = #{userId}")
    void markRead(@Param("userId") Long userId, @Param("id") Long id);

    @Select("DELETE FROM user_message WHERE id = #{id} AND receiver_id = #{userId}")
    void deleteByIdAndReceiverId(@Param("id") Long id, @Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM user_message WHERE receiver_id = #{userId} AND is_read = 0")
    int countUnread(Long userId);
}
