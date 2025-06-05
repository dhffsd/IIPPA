package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    @Select("select id,username,avatar_url,avatar_type,created_at,updated_at from users where username=#{username}")
    Users getUserInfo(String username);
}
