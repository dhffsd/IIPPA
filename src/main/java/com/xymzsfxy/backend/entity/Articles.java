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
 * @Date 2025-06-11 12:12:54 
 */
@Entity
@Table ( name ="articles" )
public class Articles  implements Serializable {

	private static final long serialVersionUID =  5317983882371913536L;

	/**
	 * 文章ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 作者用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 文章标题
	 */
   	@Column(name = "title" )
	private String title;

	/**
	 * 文章内容
	 */
   	@Column(name = "content" )
	private String content;

	/**
	 * 文章摘要
	 */
   	@Column(name = "summary" )
	private String summary;

	/**
	 * 文章分类
	 */
   	@Column(name = "category" )
	private String category;

	/**
	 * 文章状态
	 */
   	@Column(name = "status" )
	private String status;

	/**
	 * 是否置顶
	 */
   	@Column(name = "is_sticky" )
	private Integer isSticky;

	/**
	 * 是否精选
	 */
   	@Column(name = "is_featured" )
	private Integer isFeatured;

	/**
	 * 阅读数
	 */
   	@Column(name = "view_count" )
	private Long viewCount;

	/**
	 * 评论数
	 */
   	@Column(name = "comment_count" )
	private Long commentCount;

	/**
	 * 点赞数
	 */
   	@Column(name = "like_count" )
	private Long likeCount;

	/**
	 * 创建时间
	 */
   	@Column(name = "created_at" )
	private Date createdAt;

	/**
	 * 更新时间
	 */
   	@Column(name = "updated_at" )
	private Date updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public Integer getIsSticky() {
    return isSticky;
  }

  public void setIsSticky(Integer isSticky) {
    this.isSticky = isSticky;
  }


  public Integer getIsFeatured() {
    return isFeatured;
  }

  public void setIsFeatured(Integer isFeatured) {
    this.isFeatured = isFeatured;
  }


  public Long getViewCount() {
    return viewCount;
  }

  public void setViewCount(Long viewCount) {
    this.viewCount = viewCount;
  }


  public Long getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(Long commentCount) {
    this.commentCount = commentCount;
  }


  public Long getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(Long likeCount) {
    this.likeCount = likeCount;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }


  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}
