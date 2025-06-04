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
@Table ( name ="password_reset_tokens" )
public class ResetTokens  implements Serializable {

	private static final long serialVersionUID =  4712929207433384196L;

	/**
	 * 重置令牌ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 重置令牌
	 */
   	@Column(name = "token" )
	private String token;

	/**
	 * 过期时间
	 */
   	@Column(name = "expires_at" )
	private Date expiresAt;

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


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
