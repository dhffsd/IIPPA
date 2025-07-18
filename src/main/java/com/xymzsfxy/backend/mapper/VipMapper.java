package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.dto.VipGetPointsDTO;
import com.xymzsfxy.backend.dto.VipPointsRecordDTO;
import com.xymzsfxy.backend.entity.UserVip;
import com.xymzsfxy.backend.entity.VipLevels;
import com.xymzsfxy.backend.entity.VipOrders;
import com.xymzsfxy.backend.entity.VipPointRecords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface VipMapper {

    @Select("SELECT * FROM user_vip WHERE user_id = #{userId} LIMIT 1")
    UserVip getUserVipInfo(Long userId);

    @Select("select * from vip_levels where id = #{levelId}")
    VipLevels selectVipLevelById(Integer levelId);

    @Insert("INSERT INTO vip_point_records (user_id, points, source, description, created_at) " +
            "VALUES (#{userId}, #{points}, #{source}, #{description}, #{createdAt})")
    void insert(VipPointRecords record);

    @Insert("INSERT INTO vip_orders (user_id, level_id, order_sn, duration, payment_method, order_status, " +
            "payment_amount, created_at, payment_time) " +
            "VALUES (#{userId}, #{levelId}, #{orderSn}, #{duration}, " +
            "#{paymentMethod}, #{orderStatus}, #{paymentAmount}, " +
            "#{createdAt}, #{paymentTime})")
    void insertOrder(VipOrders order);

    @Select("select * from user_vip where user_id = #{userId}")
    UserVip selectByUserId(Long userId);

    @Insert("INSERT INTO user_vip (user_id, level_id, status, points, expire_date, created_at, updated_at) " +
            "VALUES (#{userId}, #{levelId}, #{status}, #{points}, #{expireDate}, " +
            "NOW(), NOW())")
    void insertUserVip(UserVip userVip);

    @Update({
            "<script>",
            "UPDATE user_vip",
            "<set>",
            "<if test='levelId != null'>level_id = #{levelId},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "<if test='points != null'>points = #{points},</if>",
            "<if test='expireDate != null'>expire_date = #{expireDate},</if>",
            "updated_at = NOW()",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    void updateById(UserVip userVip);

    @Select("select * from vip_levels")
    List<VipLevels> selectAllByNull();

    @Select("select points from user_vip where user_id = #{userId}")
    VipGetPointsDTO getUserVipPoints(Long userId);

    @Select("SELECT points, source, description, created_at AS createdAt " +
            "FROM vip_point_records " +
            "WHERE user_id = #{userId} " +
            "ORDER BY created_at DESC")
    List<VipPointsRecordDTO> selectPointsRecordById(Long userId);
}
