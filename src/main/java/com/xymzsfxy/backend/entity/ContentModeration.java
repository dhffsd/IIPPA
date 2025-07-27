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
 * @Date 2025-07-27 10:02:40 
 */
@Entity
@Table ( name ="content_moderation" )
public class ContentModeration  implements Serializable {

	private static final long serialVersionUID =  2843781163673624716L;

	@Id
   	@Column(name = "id" )
	private Long id;

	/**
	 * 内容ID
	 */
   	@Column(name = "content_id" )
	private String contentId;

	/**
	 * 内容类型
	 */
   	@Column(name = "content_type" )
	private String contentType;

	/**
	 * 内容标题
	 */
   	@Column(name = "title" )
	private String title;

	/**
	 * 内容正文
	 */
   	@Column(name = "content" )
	private String content;

	/**
	 * 作者ID
	 */
   	@Column(name = "author_id" )
	private Long authorId;

	/**
	 * 作者用户名
	 */
   	@Column(name = "author_name" )
	private String authorName;

	/**
	 * 内容分类
	 */
   	@Column(name = "category" )
	private String category;

	/**
	 * 审核决定
	 */
   	@Column(name = "decision" )
	private String decision;

	/**
	 * 置信度
	 */
   	@Column(name = "confidence" )
	private BigDecimal confidence;

	/**
	 * 违规类型(JSON)
	 */
   	@Column(name = "violations" )
	private String violations;

	/**
	 * AI审核原因
	 */
   	@Column(name = "ai_reason" )
	private String aiReason;

	/**
	 * 人工审核原因
	 */
   	@Column(name = "manual_reason" )
	private String manualReason;

	/**
	 * 状态
	 */
   	@Column(name = "status" )
	private String status;

	/**
	 * 审核员ID
	 */
   	@Column(name = "reviewer_id" )
	private Long reviewerId;

	/**
	 * 审核级别
	 */
   	@Column(name = "moderation_level" )
	private String moderationLevel;

	/**
	 * 创建时间
	 */
   	@Column(name = "created_at" )
	private Date createdAt;

	/**
	 * 审核时间
	 */
   	@Column(name = "reviewed_at" )
	private Date reviewedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }


  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }


  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }


  public String getDecision() {
    return decision;
  }

  public void setDecision(String decision) {
    this.decision = decision;
  }


  public BigDecimal getConfidence() {
    return confidence;
  }

  public void setConfidence(BigDecimal confidence) {
    this.confidence = confidence;
  }


  public String getViolations() {
    return violations;
  }

  public void setViolations(String violations) {
    this.violations = violations;
  }


  public String getAiReason() {
    return aiReason;
  }

  public void setAiReason(String aiReason) {
    this.aiReason = aiReason;
  }


  public String getManualReason() {
    return manualReason;
  }

  public void setManualReason(String manualReason) {
    this.manualReason = manualReason;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public Long getReviewerId() {
    return reviewerId;
  }

  public void setReviewerId(Long reviewerId) {
    this.reviewerId = reviewerId;
  }


  public String getModerationLevel() {
    return moderationLevel;
  }

  public void setModerationLevel(String moderationLevel) {
    this.moderationLevel = moderationLevel;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }


  public Date getReviewedAt() {
    return reviewedAt;
  }

  public void setReviewedAt(Date reviewedAt) {
    this.reviewedAt = reviewedAt;
  }

}
