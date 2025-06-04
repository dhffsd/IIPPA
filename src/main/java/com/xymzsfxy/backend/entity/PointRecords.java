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
@Table ( name ="vip_point_records" )
public class PointRecords  implements Serializable {

	private static final long serialVersionUID =  7822952559890986604L;

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
	 * 变更值
	 */
   	@Column(name = "points" )
	private Integer points;

	/**
	 * 来源
	 */
   	@Column(name = "source" )
	private String source;

	/**
	 * 说明
	 */
   	@Column(name = "description" )
	private String description;

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


  public Integer getPoints() {
    return points;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }


  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
