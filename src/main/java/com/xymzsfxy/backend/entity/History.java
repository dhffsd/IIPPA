package com.xymzsfxy.backend.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.math.BigDecimal;
/** 
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-06-04 15:05:42 
 */
@Entity
@Table ( name ="price_history" )
public class History  implements Serializable {

	private static final long serialVersionUID =  5840816681422803810L;

	/**
	 * 价格记录唯一标识
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 关联商品表的商品 ID
	 */
   	@Column(name = "product_id" )
	private Long productId;

	/**
	 * 数据来源(电商平台名称)
	 */
   	@Column(name = "source" )
	private String source;

	/**
	 * 商品价格
	 */
   	@Column(name = "price" )
	private BigDecimal price;

	/**
	 * 物流费用
	 */
   	@Column(name = "logisticsCost" )
	private BigDecimal logisticsCost;

	/**
	 * 综合成本
	 */
   	@Column(name = "totalCost" )
	private BigDecimal totalCost;

	/**
	 * 商品评分，范围 0.0 到 5.0
	 */
   	@Column(name = "rating" )
	private BigDecimal rating;

	/**
	 * 商品历史最高价
	 */
   	@Column(name = "maxPrice" )
	private BigDecimal maxPrice;

	/**
	 * 商品历史最低价
	 */
   	@Column(name = "minPrice" )
	private BigDecimal minPrice;

	/**
	 * 数据爬取时间
	 */
   	@Column(name = "crawl_time" )
	private LocalDateTime crawlTime;

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


  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public BigDecimal getLogisticsCost() {
    return logisticsCost;
  }

  public void setLogisticsCost(BigDecimal logisticsCost) {
    this.logisticsCost = logisticsCost;
  }


  public BigDecimal getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(BigDecimal totalCost) {
    this.totalCost = totalCost;
  }


  public BigDecimal getRating() {
    return rating;
  }

  public void setRating(BigDecimal rating) {
    this.rating = rating;
  }


  public BigDecimal getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(BigDecimal maxPrice) {
    this.maxPrice = maxPrice;
  }


  public BigDecimal getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(BigDecimal minPrice) {
    this.minPrice = minPrice;
  }


  public LocalDateTime getCrawlTime() {
    return crawlTime;
  }

  public void setCrawlTime(LocalDateTime crawlTime) {
    this.crawlTime = crawlTime;
  }

}
