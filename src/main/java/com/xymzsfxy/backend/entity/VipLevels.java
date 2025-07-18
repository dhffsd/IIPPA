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
 * @Date 2025-06-04 15:05:42 
 */
@Entity
@Table ( name ="vip_levels" )
public class VipLevels  implements Serializable {

	private static final long serialVersionUID =  374413454696669814L;

	/**
	 * 会员等级ID
	 */
	@Id
   	@Column(name = "id" )
	private Integer id;

	/**
	 * 等级名称
	 */
   	@Column(name = "name" )
	private String name;

	/**
	 * 最低成长值
	 */
   	@Column(name = "min_points" )
	private Long minPoints;

	/**
	 * 折扣率（0.85表示85折）
	 */
   	@Column(name = "discount_rate" )
	private BigDecimal discountRate;

	/**
	 * 月费价格
	 */
   	@Column(name = "monthly_price" )
	private BigDecimal monthlyPrice;

	/**
	 * 等级标识颜色
	 */
   	@Column(name = "color" )
	private String color;

	/**
	 * 等级图标URL
	 */
   	@Column(name = "icon" )
	private String icon;

	/**
	 * 特权描述
	 */
   	@Column(name = "benefits" )
	private String benefits;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Long getMinPoints() {
    return minPoints;
  }

  public void setMinPoints(Long minPoints) {
    this.minPoints = minPoints;
  }


  public BigDecimal getDiscountRate() {
    return discountRate;
  }

  public void setDiscountRate(BigDecimal discountRate) {
    this.discountRate = discountRate;
  }


  public BigDecimal getMonthlyPrice() {
    return monthlyPrice;
  }

  public void setMonthlyPrice(BigDecimal monthlyPrice) {
    this.monthlyPrice = monthlyPrice;
  }


  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }


  public String getBenefits() {
    return benefits;
  }

  public void setBenefits(String benefits) {
    this.benefits = benefits;
  }

}
