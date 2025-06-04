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
 * @Date 2025-06-04 15:05:41 
 */
@Entity
@Table ( name ="comment_likes" )
public class Likes  implements Serializable {

	private static final long serialVersionUID =  4470454119874423780L;

	/**
	 * 点赞用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 评论ID
	 */
   	@Column(name = "comment_id" )
	private Long commentId;

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


  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
