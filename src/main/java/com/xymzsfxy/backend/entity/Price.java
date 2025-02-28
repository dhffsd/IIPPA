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
@Table ( name ="price" )
public class Price  implements Serializable {

	private static final long serialVersionUID =  999860294661444474L;

	/**
	 * 价格ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 商品ID
	 */
   	@Column(name = "product_id" )
	private Long productId;

	/**
	 * 供应商ID
	 */
   	@Column(name = "supplier_id" )
	private Long supplierId;

	/**
	 * 价格
	 */
   	@Column(name = "price" )
	private BigDecimal price;

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


  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }


  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

}
