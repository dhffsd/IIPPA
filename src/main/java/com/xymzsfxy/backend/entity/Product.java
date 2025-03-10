package com.xymzsfxy.backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.math.BigDecimal;
/** 
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-03-09 14:46:13 
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table ( name ="product" )
public class Product  implements Serializable {

	private static final long serialVersionUID =  481064042210326062L;

	/**
	 * 商品ID
	 */
	@Id
   	@Column(name = "id" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 商品名称
	 */
   	@Column(name = "name" )
	private String name;

	/**
	 * 商品类别
	 */
   	@Column(name = "category" )
	private String category;

	/**
	 * 第三方平台商品ID
	 */
   	@Column(name = "external_id" )
	private String externalId;

	/**
	 * 商品描述
	 */
   	@Column(name = "description" )
	private String description;

	/**
	 * 最新的价格
	 */
   	@Column(name = "latest_price" )
	private BigDecimal latestPrice;

	/**
	 * 商品图片链接
	 */
   	@Column(name = "image_url" )
	private String imageUrl;

	/**
	 * 创建时间
	 */
   	@Column(name = "created_time" )
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date createdTime;

	/**
	 * 更新时间
	 */
   	@Column(name = "updated_time" )
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private LocalDateTime updatedTime;

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


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }


  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public BigDecimal getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(BigDecimal latestPrice) {
    this.latestPrice = latestPrice;
  }


  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }


  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }


  public LocalDateTime getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(LocalDateTime updatedTime) {
    this.updatedTime = updatedTime;
  }

}
