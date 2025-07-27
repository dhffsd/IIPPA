package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.EmailVerificationTokens;
import com.xymzsfxy.backend.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {
//    添加
    @Insert("insert into users(username,password_hash,created_at,updated_at)" +
            "values(#{username},#{password},now(),now())")
    void add(String username, String password);
//    根据用户名查询
    @Select("SELECT * FROM users WHERE username LIKE CONCAT('%', #{username}, '%') LIMIT #{offset}, #{size}")
    List<Users> findByUserName(String username, Integer offset, Integer size);
    @Select("SELECT * FROM users LIMIT #{offset}, #{size}")
    List<Users> getAllUserInfo(Integer offset , Integer size);
    @Select("select count(*) from users")
    Long getInfoCount();

    @Select("SELECT COUNT(*) FROM users WHERE username LIKE CONCAT('%', #{username}, '%')")
    Long getNameCount(String username);

    @Select("select * from users where username=#{username}")
    Users findByUserRegisterName(String username);

    @Select("select password_hash from users where username=#{username}")
    String getPassword(String username);

    @Select("select id,username,email,phone,avatar_url,avatar_type,company,introduction,region,gender,role,created_at,updated_at from users where username=#{username}")
    Users getUserInfo(String username);

    @Update("update users set username=#{username},email=#{email},phone=#{phone},company=#{company},introduction=#{introduction},region=#{region},gender=#{gender},updated_at=now() where id=#{userId} ")
    Integer updateUserInfo(Long userId, String username, String email, String phone, String company, String introduction, String region, String gender);

    @Select({
            "<script>",
            "SELECT id, username, avatar_url, role, badges FROM users",
            "<if test='userIds != null and !userIds.isEmpty()'>",
            "WHERE id IN ",
            "<foreach collection='userIds' item='userId' open='(' separator=',' close=')'>",
            "#{userId}",
            "</foreach>",
            "</if>",
            "</script>"
    })
    List<Users> selectById(List<Long> userIds);

    @Select("select id, username, avatar_url, role from users where id=#{userId}")
    Users selectDetailById(Long userId);

    @Select("select * from users where id=#{userId}")
    Users selectAvailablePointsById(Long userId);

    @Update("update users set available_points=#{user.availablePoints} where id = #{userId} ")
    void updateById(Users user, Long userId);

    @Update("update users set role = 'vip' where id = #{userId}")
    void updateUserRole(Long userId);

    @Update("update users set available_points = available_points + #{availablePoints} where id = #{userIdFromToken}")
    void updatePoints(Long userIdFromToken, Long availablePoints);

    @Update("UPDATE users SET avatar_url = #{avatarUrl}, avatar_type = #{avatarType}, updated_at = NOW() WHERE id = #{userId}")
    void updateAvatar(Long userId, String avatarUrl, String avatarType);

    @Insert("insert into email_verification_tokens(user_id, token, expires_at, created_at)" +
            "values(#{userId}, #{code}, #{exdate}, now())")
    void saveMailToken(Long userId, String code, Date exdate);

    @Select("SELECT * FROM email_verification_tokens " +
            "WHERE user_id = #{userId} AND token = #{code} " +
            "ORDER BY created_at DESC " +
            "LIMIT 1")
    EmailVerificationTokens findTopByUserIdAndTokenOrderByCreatedAtDesc(Long userId, String code);

    @Update("update users set email = #{mail} where id = #{userId}")
    void setMailByUserId(String mail, Long userId);

    @Select("select * from users where email = #{mail}")
    Users selectEmail(String mail);

    @Insert("insert into email_verification_tokens(user_id, token, expires_at, created_at)" +
            "values(#{id}, #{code}, #{exdate}, now())")
    void insertLoginMailCode(Long id, String code, Date exdate);
}
