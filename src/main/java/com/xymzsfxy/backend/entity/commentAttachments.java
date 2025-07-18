package com.xymzsfxy.backend.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
import java.io.InputStream;
/** 
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-06-17 21:43:42 
 */
@Entity
@Table ( name ="comment_attachments" )
public class commentAttachments  implements Serializable {

	private static final long serialVersionUID =  6386010725581719918L;

	/**
	 * 附件ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 评论ID
	 */
   	@Column(name = "comment_id" )
	private Long commentId;

	/**
	 * 文件地址
	 */
   	@Column(name = "file_url" )
	private String fileUrl;

	/**
	 * 文件类型
	 */
   	@Column(name = "file_type" )
	private String fileType;

	/**
	 * 缩略图地址
	 */
   	@Column(name = "thumbnail_url" )
	private String thumbnailUrl;

	/**
	 * 显示顺序
	 */
   	@Column(name = "display_order" )
	private Integer displayOrder;

	/**
	 * 创建时间
	 */
   	@Column(name = "created_at" )
	private Date createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }


  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }


  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }


  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }


  public Integer getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
