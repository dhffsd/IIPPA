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
 * @Date 2025-02-23 23:17:39 
 */
@Entity
@Table ( name ="purchase_history" )
public class History  implements Serializable {

	private static final long serialVersionUID =  3739701562270047844L;

	/**
	 * 采购历史ID
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
	 * 商品ID
	 */
   	@Column(name = "product_id" )
	private Long productId;

	/**
	 * 价格
	 */
   	@Column(name = "price" )
	private BigDecimal price;

	/**
	 * 采购时间
	 */
   	@Column(name = "purchase_time" )
	private Date purchaseTime;

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


  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public Date getPurchaseTime() {
    return purchaseTime;
  }

  public void setPurchaseTime(Date purchaseTime) {
    this.purchaseTime = purchaseTime;
  }

}
