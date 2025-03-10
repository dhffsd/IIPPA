package com.xymzsfxy.backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.math.BigDecimal;
/** 
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-03-09 00:32:10 
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table ( name ="price_history" )
public class PriceHistory  implements Serializable {

	private static final long serialVersionUID =  6972887328421996698L;

	@Id
   	@Column(name = "id" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

   	@Column(name = "product_id" )
	private Long productId;

	/**
	 * 数据来源(电商平台名称)
	 */
   	@Column(name = "source" )
	private String source;

   	@Column(name = "price" )
	private BigDecimal price;

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


  public LocalDateTime getCrawlTime() {
    return crawlTime;
  }

  public void setCrawlTime(LocalDateTime crawlTime) {
    this.crawlTime = crawlTime;
  }

}
