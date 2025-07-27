package com.xymzsfxy.backend.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
/** 
 * @team mackie Studio 
 * @Author 无深 
 * @Date 2025-07-22 16:20:06 
 */
@Entity
@Table ( name ="user_message" )
public class UserMessage  implements Serializable {

	private static final long serialVersionUID =  635180284866001578L;

	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 接收消息的用户ID
	 */
   	@Column(name = "receiver_id" )
	private Long receiverId;

	/**
	 * 发送消息的用户ID (0 for system)
	 */
   	@Column(name = "sender_id" )
	private Long senderId;

	/**
	 * 消息类型 (REPLY, SYSTEM)
	 */
   	@Column(name = "type" )
	private String type;

	/**
	 * 消息内容
	 */
   	@Column(name = "content" )
	private String content;

	/**
	 * 是否已读 (0-未读, 1-已读)
	 */
   	@Column(name = "is_read" )
	private Integer isRead;

	/**
	 * 关联实体类型 (e.g., POST, COMMENT)
	 */
   	@Column(name = "related_entity_type" )
	private String relatedEntityType;

	/**
	 * 关联实体ID
	 */
   	@Column(name = "related_entity_id" )
	private Long relatedEntityId;

   	@Column(name = "created_at" )
	private LocalDateTime createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }


  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public Integer getIsRead() {
    return isRead;
  }

  public void setIsRead(Integer isRead) {
    this.isRead = isRead;
  }


  public String getRelatedEntityType() {
    return relatedEntityType;
  }

  public void setRelatedEntityType(String relatedEntityType) {
    this.relatedEntityType = relatedEntityType;
  }


  public Long getRelatedEntityId() {
    return relatedEntityId;
  }

  public void setRelatedEntityId(Long relatedEntityId) {
    this.relatedEntityId = relatedEntityId;
  }


  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
