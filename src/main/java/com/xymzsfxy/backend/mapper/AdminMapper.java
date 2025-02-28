package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where username=#{username}")
    Admin FindByUserName(String username);

    @Insert("Insert into admin(username,password,email,phone,role,created_time,updated_time)" +
            "values(#{username},#{password},#{email},#{phone},#{role},now(),now())")
    void register(String username, String password, String email, String phone, String role);
}
