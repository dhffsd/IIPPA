package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.Product;
import org.apache.ibatis.annotations.*;

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
}
