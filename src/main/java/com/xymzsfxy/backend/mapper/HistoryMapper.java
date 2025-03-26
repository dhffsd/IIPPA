package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.History;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HistoryMapper {
    // 获取所有数据
    @Select("select * from price_history")
    List<History> findAll();

    @Update({
            "<script>",
            "UPDATE price_history",
            "SET",
            "price = CASE",
            "  <foreach collection='list' item='item'>",
            "    WHEN id = #{item.id} THEN #{item.price}",
            "  </foreach>",
            "END,",
            "logisticsCost = CASE",
            "  <foreach collection='list' item='item'>",
            "    WHEN id = #{item.id} THEN #{item.logisticsCost}",
            "  </foreach>",
            "END,",
            "totalCost = CASE",
            "  <foreach collection='list' item='item'>",
            "    WHEN id = #{item.id} THEN #{item.totalCost}",
            "  </foreach>",
            "END,",
            "rating = CASE",
            "  <foreach collection='list' item='item'>",
            "    WHEN id = #{item.id} THEN #{item.rating}",
            "  </foreach>",
            "END,",
            "source = CASE",
            "  <foreach collection='list' item='item'>",
            "    WHEN id = #{item.id} THEN #{item.source}",
            "  </foreach>",
            "END,",
            "maxPrice = CASE",
            "  <foreach collection='list' item='item'>",
            "    WHEN id = #{item.id} THEN #{item.maxPrice}",
            "  </foreach>",
            "END,",
            "minPrice = CASE",
            "  <foreach collection='list' item='item'>",
            "    WHEN id = #{item.id} THEN #{item.minPrice}",
            "  </foreach>",
            "END,",
            "crawl_time = CASE",
            "  <foreach collection='list' item='item'>",
            "    WHEN id = #{item.id} THEN #{item.crawlTime}",
            "  </foreach>",
            "END",
            "WHERE id IN",
            "  <foreach collection='list' item='item' open='(' separator=',' close=')'>",
            "    #{item.id}",
            "  </foreach>",
            "</script>"
    })
    void batchUpdate(List<History> priceHistories);
}