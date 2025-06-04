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
 * @Date 2025-06-04 15:05:41 
 */
@Entity
@Table ( name ="avatar_storage" )
public class Storage  implements Serializable {

	private static final long serialVersionUID =  3877919799377365292L;

	/**
	 * 存储ID
	 */
	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 关联用户ID
	 */
   	@Column(name = "user_id" )
	private Long userId;

	/**
	 * 存储文件名
	 */
   	@Column(name = "file_name" )
	private String fileName;

	/**
	 * 文件大小(字节)
	 */
   	@Column(name = "file_size" )
	private Long fileSize;

	/**
	 * 文件类型(MIME)
	 */
   	@Column(name = "file_type" )
	private String fileType;

	/**
	 * 存储路径
	 */
   	@Column(name = "storage_path" )
	private String storagePath;

	/**
	 * 上传时间
	 */
   	@Column(name = "created_at" )
	private Date createdAt;

	/**
	 * 更新时间
	 */
   	@Column(name = "updated_at" )
	private Date updatedAt;

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


  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


  public Long getFileSize() {
    return fileSize;
  }

  public void setFileSize(Long fileSize) {
    this.fileSize = fileSize;
  }


  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }


  public String getStoragePath() {
    return storagePath;
  }

  public void setStoragePath(String storagePath) {
    this.storagePath = storagePath;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }


  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}
