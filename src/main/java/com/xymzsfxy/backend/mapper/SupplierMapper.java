package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.Supplier;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SupplierMapper {
    @Select("select * from supplier where name=#{name}")
    Supplier findBySupplierName(String name);

    @Insert("INSERT INTO supplier(name,contact,address,rating,created_at,updated_at) " +
            "VALUES(#{name},#{contact},#{address},#{rating},now(),now())")
    void add(String name, String contact, String address, BigDecimal rating);

    @Select("select * from supplier")
    List<Supplier> getAll();

    @Delete("delete from supplier where id=#{id}")
    void delete(Long id);

    @Update("update supplier set name=#{name},address=#{address},contact=#{contact},updated_at=now() where id=#{id}")
    void update(Long id, String name, String address, String contact);

    @Select("select count(*) from supplier")
    Long getTotalCount();
}
