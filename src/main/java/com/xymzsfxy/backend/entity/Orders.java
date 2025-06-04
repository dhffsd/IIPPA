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
@Table ( name ="vip_orders" )
public class Orders  implements Serializable {

	private static final long serialVersionUID =  59564935063263637L;

	/**
	 * 订单ID
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
	 * 开通等级
	 */
   	@Column(name = "level_id" )
	private Integer levelId;

	/**
	 * 订单编号
	 */
   	@Column(name = "order_sn" )
	private String orderSn;

	/**
	 * 支付金额
	 */
   	@Column(name = "payment_amount" )
	private BigDecimal paymentAmount;

	/**
	 * 开通时长（月数）
	 */
   	@Column(name = "duration" )
	private Integer duration;

	/**
	 * 支付方式
	 */
   	@Column(name = "payment_method" )
	private String paymentMethod;

	/**
	 * 支付时间
	 */
   	@Column(name = "payment_time" )
	private Date paymentTime;

	/**
	 * 订单状态
	 */
   	@Column(name = "order_status" )
	private String orderStatus;

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


  public Integer getLevelId() {
    return levelId;
  }

  public void setLevelId(Integer levelId) {
    this.levelId = levelId;
  }


  public String getOrderSn() {
    return orderSn;
  }

  public void setOrderSn(String orderSn) {
    this.orderSn = orderSn;
  }


  public BigDecimal getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(BigDecimal paymentAmount) {
    this.paymentAmount = paymentAmount;
  }


  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }


  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }


  public Date getPaymentTime() {
    return paymentTime;
  }

  public void setPaymentTime(Date paymentTime) {
    this.paymentTime = paymentTime;
  }


  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
