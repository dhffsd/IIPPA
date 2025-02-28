package com.xymzsfxy.backend.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
/** 
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-02-23 23:17:39 
 */
@Entity
@Table ( name ="favorite" )
public class Favorite  implements Serializable {

	private static final long serialVersionUID =  2650530802871307336L;

	/**
	 * 收藏ID
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

}
