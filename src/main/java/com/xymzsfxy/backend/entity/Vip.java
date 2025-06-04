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
@Table ( name ="user_vip" )
public class Vip  implements Serializable {

	private static final long serialVersionUID =  1694061137730719951L;

	/**
	 * 记录ID
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
	 * 会员等级ID
	 */
   	@Column(name = "level_id" )
	private Integer levelId;

	/**
	 * 会员状态
	 */
   	@Column(name = "status" )
	private String status;

	/**
	 * 成长值
	 */
   	@Column(name = "points" )
	private Long points;

	/**
	 * 到期日期
	 */
   	@Column(name = "expire_date" )
	private Date expireDate;

	/**
	 * 首次开通时间
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


  public Integer getLevelId() {
    return levelId;
  }

  public void setLevelId(Integer levelId) {
    this.levelId = levelId;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public Long getPoints() {
    return points;
  }

  public void setPoints(Long points) {
    this.points = points;
  }


  public Date getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
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
