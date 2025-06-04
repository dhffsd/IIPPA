package com.xymzsfxy.backend.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
/** 
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-06-04 15:05:42 
 */
@Entity
@Table ( name ="supplier" )
public class Supplier  implements Serializable {

	private static final long serialVersionUID =  3508827122980523478L;

	/**
	 * 供应商ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 供应商名称
	 */
   	@Column(name = "name" )
	private String name;

	/**
	 * 联系方式
	 */
   	@Column(name = "contact" )
	private String contact;

	/**
	 * 评分（0-10）
	 */
   	@Column(name = "rating" )
	private BigDecimal rating;

	/**
	 * 联系地址
	 */
   	@Column(name = "address" )
	private String address;

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


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }


  public BigDecimal getRating() {
    return rating;
  }

  public void setRating(BigDecimal rating) {
    this.rating = rating;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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
