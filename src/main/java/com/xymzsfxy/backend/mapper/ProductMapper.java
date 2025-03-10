package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Insert("Insert into product(name,category,description,image_url,created_time,updated_time)" +
            "value(#{name},#{category},#{description},#{imageUrl},now(),now())")
    void add(String name, String category, String description,String imageUrl);

    @Update("update product set name=#{name},category=#{category}," +
            "description=#{description},image_url=#{imageUrl}, updated_time=now() where id=#{id}")
    void update(Long id, String name, String category, String description, String imageUrl);

    @Delete("delete from product where id=#{id}")
    void delete(Long id);

    @Select("select * from product where id=#{id}")
    Product FindByProductId(Long id);

    @Select("SELECT * FROM product LIMIT #{offset}, #{size}")
    List<Product> get(Integer offset, Integer size);

    @Select("select count(*) from product")
    Long getTotalCount();

    @Select("SELECT * FROM product WHERE name LIKE CONCAT('%', #{name}, '%') LIMIT #{offset}, #{size}")
    List<Product> FindByProductName(String name,Integer offset,Integer size);

    @Select("SELECT COUNT(*) FROM product WHERE name LIKE CONCAT('%', #{name}, '%')")
    Long getNameCount(String name);
}
