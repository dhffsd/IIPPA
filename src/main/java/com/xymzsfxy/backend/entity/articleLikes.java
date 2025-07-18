package com.xymzsfxy.backend.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
/** 
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-06-17 21:42:42 
 */
@Entity
@Table ( name ="article_likes" )
public class articleLikes  implements Serializable {

	private static final long serialVersionUID =  1384290551244863446L;

	/**
	 * 点赞用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 文章ID
	 */
   	@Column(name = "article_id" )
	private Long articleId;

	/**
	 * 点赞时间
	 */
   	@Column(name = "created_at" )
	private Date createdAt;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public Long getArticleId() {
    return articleId;
  }

  public void setArticleId(Long articleId) {
    this.articleId = articleId;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
