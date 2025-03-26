package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
//    添加
    @Insert("insert into user(username,password,email,user_pic,create_time,update_time)" +
            "values(#{username},#{password},#{email},#{userPic},now(),now())")
    void add(String username, String password, String userPic, String email);
//    根据用户名查询
    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%', #{username}, '%') LIMIT #{offset}, #{size}")
    List<User> findByUserName(String username, Integer offset, Integer size);
    @Select("SELECT * FROM user LIMIT #{offset}, #{size}")
    List<User> getAllUserInfo(Integer offset , Integer size);
    @Select("select count(*) from user")
    Long getInfoCount();

    @Select("SELECT COUNT(*) FROM user WHERE username LIKE CONCAT('%', #{username}, '%')")
    Long getNameCount(String username);

    @Select("select * from user where username=#{username}")
    User findByUserRegisterName(String username);
}
