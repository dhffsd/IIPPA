package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.SignIn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SignInMapper {
    @Select("Select count(*) from user_sign_in where user_id=#{userIdFromToken} AND sign_in_date=#{today}")
    Long existsByUserId(Long userIdFromToken, LocalDate today);

    @Insert("INSERT INTO user_sign_in(user_id, sign_in_date, sign_in_time, points, is_repair, create_time, update_time)\n" +
            "        VALUES(#{userIdFromToken}, #{today}, #{signInTime}, #{isR}, #{repair}, now(), now())")
    void insert(Long userIdFromToken, LocalDate today, LocalDateTime signInTime, Long isR, int repair);

    @Select("SELECT * FROM user_sign_in\n" +
            "    WHERE user_id = #{userIdFromToken} AND sign_in_date BETWEEN #{start} AND #{end}\n" +
            "    ORDER BY sign_in_date")
    List<SignIn> selectByUserId(Long userIdFromToken, LocalDate start, LocalDate end);
}
