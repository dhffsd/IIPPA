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
 * @Date 2025-07-01 14:16:01 
 */
@Entity
@Table ( name ="user_sign_in" )
public class SignIn  implements Serializable {

	private static final long serialVersionUID =  1731871914636697381L;

	/**
	 * 主键
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
	 * 签到日期
	 */
   	@Column(name = "sign_in_date" )
	private Date signInDate;

	/**
	 * 签到时间
	 */
   	@Column(name = "sign_in_time" )
	private Date signInTime;

	/**
	 * 本次签到获得积分
	 */
   	@Column(name = "points" )
	private Long points;

	/**
	 * 是否为补签（0否1是）
	 */
   	@Column(name = "is_repair" )
	private Integer isRepair;

	/**
	 * 备注
	 */
   	@Column(name = "remark" )
	private String remark;

	/**
	 * 创建时间
	 */
   	@Column(name = "create_time" )
	private Date createTime;

	/**
	 * 更新时间
	 */
   	@Column(name = "update_time" )
	private Date updateTime;

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


  public Date getSignInDate() {
    return signInDate;
  }

  public void setSignInDate(Date signInDate) {
    this.signInDate = signInDate;
  }


  public Date getSignInTime() {
    return signInTime;
  }

  public void setSignInTime(Date signInTime) {
    this.signInTime = signInTime;
  }


  public Long getPoints() {
    return points;
  }

  public void setPoints(Long points) {
    this.points = points;
  }


  public Integer getIsRepair() {
    return isRepair;
  }

  public void setIsRepair(Integer isRepair) {
    this.isRepair = isRepair;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

}
