package com.xymzsfxy.backend.mapper;

import com.xymzsfxy.backend.entity.Articles;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into articles(user_id,title,content,category,created_at,updated_at) " +
            "values(#{userId},#{title},#{content},#{category},now(),now())  ")
    void articleCreate(Long userId, String title, String content, String category);

    @Select("<script>" +
            "SELECT * FROM articles " +
            "<where> " +
            "   <if test='category != null and category != \"\"'> " +
            "       AND category = #{category} " +
            "   </if> " +
            "</where> " +
            "ORDER BY " +
            "   CASE WHEN is_sticky = 1 THEN 0 ELSE 1 END, " + // 置顶文章优先
            "   created_at DESC " +
            "LIMIT #{offset}, #{pageSize} " +
            "</script>")
    List<Articles> selectByPage(String category, Integer offset, Integer pageSize);

    @Select("select count(*) from articles where category=#{category}")
    Long countByCategory(String category);

    @Select("select count(*) from articles")
    Long countAll();

    @Select("select * from articles where id=#{id}")
    Articles selectArticleById(String id);

    @Update("UPDATE articles SET view_count = view_count + 1 WHERE id = #{id}")
    void incrementViewCount(String id);
}
