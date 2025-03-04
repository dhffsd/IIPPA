package com.xymzsfxy.backend.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;

/**
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-02-23 23:17:39 
 */
@Entity
@Table ( name ="product" )
public class Product  implements Serializable {

	private static final long serialVersionUID =  3658265919353969595L;

	/**
	 * 商品ID
	 */
	@Id
   	@Column(name = "id" )
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
	 * 商品描述
	 */
   	@Column(name = "description" )
	private String description;

	/**
	 * 商品图片链接
	 */
   	@Column(name = "image_url" )
	private String imageUrl;

    @Column(name = "created_time" )
    private Date createdTime;

    @Column(name = "updated_time" )
    private Date updatedTime;

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


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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


    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}
