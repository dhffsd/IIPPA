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
@Table ( name ="favorite_items" )
public class Items  implements Serializable {

	private static final long serialVersionUID =  2529045594394312383L;

	/**
	 * 收藏项ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 所属收藏夹ID
	 */
   	@Column(name = "folder_id" )
	private Long folderId;

	/**
	 * 收藏类型
	 */
   	@Column(name = "item_type" )
	private String itemType;

	/**
	 * 收藏对象ID（根据类型指向不同表）
	 */
   	@Column(name = "item_id" )
	private String itemId;

	/**
	 * 收藏项标题
	 */
   	@Column(name = "title" )
	private String title;

	/**
	 * 收藏项图片
	 */
   	@Column(name = "image_url" )
	private String imageUrl;

	/**
	 * 收藏项原始链接
	 */
   	@Column(name = "source_url" )
	private String sourceUrl;

	/**
	 * 额外扩展信息
	 */
   	@Column(name = "extra_info" )
	private String extraInfo;

	/**
	 * 收藏时间
	 */
   	@Column(name = "created_at" )
	private Date createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getFolderId() {
    return folderId;
  }

  public void setFolderId(Long folderId) {
    this.folderId = folderId;
  }


  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }


  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }


  public String getSourceUrl() {
    return sourceUrl;
  }

  public void setSourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
  }


  public String getExtraInfo() {
    return extraInfo;
  }

  public void setExtraInfo(String extraInfo) {
    this.extraInfo = extraInfo;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
