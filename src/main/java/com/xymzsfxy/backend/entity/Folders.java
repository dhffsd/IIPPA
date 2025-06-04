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
@Table ( name ="favorites_folders" )
public class Folders  implements Serializable {

	private static final long serialVersionUID =  3371492036153512107L;

	/**
	 * 收藏夹ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 所属用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 收藏夹名称
	 */
   	@Column(name = "name" )
	private String name;

	/**
	 * 收藏夹描述
	 */
   	@Column(name = "description" )
	private String description;

	/**
	 * 是否为默认收藏夹
	 */
   	@Column(name = "is_default" )
	private Integer isDefault;

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


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public Integer getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Integer isDefault) {
    this.isDefault = isDefault;
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
