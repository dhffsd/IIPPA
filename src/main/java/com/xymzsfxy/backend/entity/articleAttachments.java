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
 * @Date 2025-06-11 12:12:54 
 */
@Entity
@Table ( name ="article_attachments" )
public class articleAttachments  implements Serializable {

	private static final long serialVersionUID =  683057219989663596L;

	/**
	 * 附件ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 文章ID
	 */
   	@Column(name = "article_id" )
	private Long articleId;

	/**
	 * 文件地址
	 */
   	@Column(name = "file_url" )
	private String fileUrl;

	/**
	 * 文件类型
	 */
   	@Column(name = "file_type" )
	private InputStream fileType;

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


  public Long getArticleId() {
    return articleId;
  }

  public void setArticleId(Long articleId) {
    this.articleId = articleId;
  }


  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }


  public InputStream getFileType() {
    return fileType;
  }

  public void setFileType(InputStream fileType) {
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
