package com.xymzsfxy.backend.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.math.BigDecimal;
/** 
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-02-23 23:17:39 
 */
@Entity
@Table ( name ="supplier" )
public class Supplier  implements Serializable {

	private static final long serialVersionUID =  4820758155415849491L;

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
	 * 信用评分
	 */
   	@Column(name = "rating" )
	private BigDecimal rating;

	/**
	 * 联系方式
	 */
   	@Column(name = "contact" )
	private String contact;

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


  public BigDecimal getRating() {
    return rating;
  }

  public void setRating(BigDecimal rating) {
    this.rating = rating;
  }


  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

}
