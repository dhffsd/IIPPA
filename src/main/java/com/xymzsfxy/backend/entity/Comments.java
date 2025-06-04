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
@Table ( name ="comments" )
public class Comments  implements Serializable {

	private static final long serialVersionUID =  9128876675645008126L;

	/**
	 * 评论ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 评论用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 评论目标类型
	 */
   	@Column(name = "target_type" )
	private String targetType;

	/**
	 * 评论目标ID（根据类型指向不同表）
	 */
   	@Column(name = "target_id" )
	private String targetId;

	/**
	 * 父评论ID（0表示顶层评论）
	 */
   	@Column(name = "parent_id" )
	private Long parentId;

	/**
	 * 根评论ID（评论树最顶层ID）
	 */
   	@Column(name = "root_id" )
	private Long rootId;

	/**
	 * 评论内容
	 */
   	@Column(name = "content" )
	private String content;

	/**
	 * 点赞数
	 */
   	@Column(name = "like_count" )
	private Long likeCount;

	/**
	 * 回复数
	 */
   	@Column(name = "reply_count" )
	private Long replyCount;

	/**
	 * 是否置顶
	 */
   	@Column(name = "is_top" )
	private Integer isTop;

	/**
	 * 是否精选
	 */
   	@Column(name = "is_featured" )
	private Integer isFeatured;

	/**
	 * 审核状态
	 */
   	@Column(name = "status" )
	private String status;

	/**
	 * 评论时间
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


  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }


  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }


  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }


  public Long getRootId() {
    return rootId;
  }

  public void setRootId(Long rootId) {
    this.rootId = rootId;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public Long getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(Long likeCount) {
    this.likeCount = likeCount;
  }


  public Long getReplyCount() {
    return replyCount;
  }

  public void setReplyCount(Long replyCount) {
    this.replyCount = replyCount;
  }


  public Integer getIsTop() {
    return isTop;
  }

  public void setIsTop(Integer isTop) {
    this.isTop = isTop;
  }


  public Integer getIsFeatured() {
    return isFeatured;
  }

  public void setIsFeatured(Integer isFeatured) {
    this.isFeatured = isFeatured;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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
