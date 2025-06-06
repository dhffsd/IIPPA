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
 * @Date 2025-06-04 15:05:42 
 */
@Entity
@Table ( name ="users" )
public class Users  implements Serializable {

	private static final long serialVersionUID =  8809140844169541930L;

	/**
	 * 用户ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 用户名
	 */
   	@Column(name = "username" )
	private String username;

	/**
	 * 邮箱
	 */
   	@Column(name = "email" )
	private String email;

	/**
	 * 手机号（可选）
	 */
   	@Column(name = "phone" )
	private String phone;

	/**
	 * 加密后的密码
	 */
   	@Column(name = "password_hash" )
	private String passwordHash;

	/**
	 * 用户头像URL
	 */
   	@Column(name = "avatar_url" )
	private String avatarUrl;

	/**
	 * 头像类型: url=外部链接, upload=上传的图片
	 */
   	@Column(name = "avatar_type" )
	private String avatarType;

	/**
	 * 邮箱验证状态
	 */
   	@Column(name = "email_verified" )
	private Integer emailVerified;

	/**
	 * 账户是否激活
	 */
   	@Column(name = "is_active" )
	private Integer isActive;

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


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }


  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }


  public String getAvatarType() {
    return avatarType;
  }

  public void setAvatarType(String avatarType) {
    this.avatarType = avatarType;
  }


  public Integer getEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(Integer emailVerified) {
    this.emailVerified = emailVerified;
  }


  public Integer getIsActive() {
    return isActive;
  }

  public void setIsActive(Integer isActive) {
    this.isActive = isActive;
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
