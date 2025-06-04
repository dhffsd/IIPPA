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
@Table ( name ="login_attempts" )
public class Attempts  implements Serializable {

	private static final long serialVersionUID =  3843803966864495263L;

	/**
	 * 登录尝试ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 关联用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 登录凭证（用户名/邮箱）
	 */
   	@Column(name = "identifier" )
	private String identifier;

	/**
	 * IP地址
	 */
   	@Column(name = "ip_address" )
	private String ipAddress;

	/**
	 * 是否成功
	 */
   	@Column(name = "success" )
	private Integer success;

	/**
	 * 用户代理信息
	 */
   	@Column(name = "user_agent" )
	private String userAgent;

	/**
	 * 尝试时间
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


  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }


  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }


  public Integer getSuccess() {
    return success;
  }

  public void setSuccess(Integer success) {
    this.success = success;
  }


  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
