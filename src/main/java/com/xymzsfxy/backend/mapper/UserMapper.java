package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
//    添加
    @Insert("insert into user(username,password,create_time,update_time)" +
            "values(#{username},#{password},now(),now())")
    void add(String username, String password);
//    根据用户名查询
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
}
