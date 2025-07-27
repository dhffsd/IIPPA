// src/main/java/com/xymzsfxy/backend/service/impl/ContentModerationServiceImpl.java
package com.xymzsfxy.backend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xymzsfxy.backend.dto.ModerationRequestDTO;
import com.xymzsfxy.backend.dto.ModerationResultDTO;
import com.xymzsfxy.backend.entity.ContentModeration;
import com.xymzsfxy.backend.mapper.ContentModerationMapper;
import com.xymzsfxy.backend.service.ContentModerationService;
import com.xymzsfxy.backend.utils.AIContentModerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;  // 添加这个导入
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContentModerationServiceImpl implements ContentModerationService {
    @Autowired
    private AIContentModerator aiModerator;

    @Autowired
    private ContentModerationMapper moderationMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public ModerationResultDTO moderateArticle(ModerationRequestDTO request) {
        return performModeration(request, "article");
    }

    @Override
    @Transactional
    public ModerationResultDTO moderateComment(ModerationRequestDTO request) {
        return performModeration(request, "comment");
    }

    private ModerationResultDTO performModeration(ModerationRequestDTO request, String type) {
        try {
            // 1. AI审核
            AIContentModerator.ModerationResult aiResult = aiModerator.moderateContent(
                    request.getContentType(),
                    request.getTitle(),
                    request.getContent(),
                    request.getCategory()
            );

            // 2. 转换为DTO
            ModerationResultDTO result = convertToDTO(aiResult, request);

            // 3. 保存审核记录
            ContentModeration moderation = saveModerationRecord(request, result);
            result.setId(moderation.getId());

            // 4. 根据审核级别决定是否需要人工审核
            if ("high".equals(request.getModerationLevel()) ||
                    aiResult.getDecision().equals("REVIEW")) {
                result.setStatus("PENDING");
            } else {
                result.setStatus(aiResult.getDecision().equals("PASS") ? "APPROVED" : "REJECTED");
            }

            return result;

        } catch (Exception e) {
            return createFallbackResult(request, e.getMessage());
        }
    }

    private ModerationResultDTO convertToDTO(AIContentModerator.ModerationResult aiResult, ModerationRequestDTO request) {
        return ModerationResultDTO.builder()
                .contentId(request.getContentId())
                .contentType(request.getContentType())
                .decision(aiResult.getDecision())
                .confidence(aiResult.getConfidence())
                .violations(aiResult.getViolations())
                .aiReason(aiResult.getReason())
                .status("PENDING")
                .createdAt(new Date())
                .build();
    }

    private ContentModeration saveModerationRecord(ModerationRequestDTO request, ModerationResultDTO result) {
        ContentModeration moderation = new ContentModeration();
        moderation.setContentId(request.getContentId());
        moderation.setContentType(request.getContentType());
        moderation.setTitle(request.getTitle());
        moderation.setContent(request.getContent());
        moderation.setAuthorId(request.getAuthorId());
        moderation.setAuthorName(request.getAuthorName());
        moderation.setCategory(request.getCategory());
        moderation.setDecision(result.getDecision());
        moderation.setConfidence(result.getConfidence());
        moderation.setAiReason(result.getAiReason());
        moderation.setStatus(result.getStatus());
        moderation.setModerationLevel(request.getModerationLevel());
        moderation.setCreatedAt(new Date());

        // 保存违规类型为JSON字符串
        try {
            moderation.setViolations(objectMapper.writeValueAsString(result.getViolations()));
        } catch (Exception e) {
            moderation.setViolations("[]");
        }

        moderationMapper.insert(moderation);
        return moderation;
    }

    private ModerationResultDTO createFallbackResult(ModerationRequestDTO request, String errorMessage) {
        return ModerationResultDTO.builder()
                .contentId(request.getContentId())
                .contentType(request.getContentType())
                .decision("REVIEW")
                .confidence(new BigDecimal("0.00"))  // 修正这里：使用 BigDecimal
                .violations(Arrays.asList("审核服务异常"))
                .aiReason("审核服务异常：" + errorMessage)
                .status("PENDING")
                .createdAt(new Date())
                .build();
    }

    @Override
    public List<ModerationResultDTO> batchModerate(List<ModerationRequestDTO> requests) {
        return requests.stream()
                .map(request -> {
                    if ("article".equals(request.getContentType())) {
                        return moderateArticle(request);
                    } else {
                        return moderateComment(request);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ModerationResultDTO> getModerationHistory(String contentId, String contentType) {
        List<ContentModeration> records = moderationMapper.findByContentIdAndType(contentId, contentType);
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void manualReview(Long moderationId, String finalDecision, Long reviewerId, String reviewNote) {
        ContentModeration moderation = moderationMapper.findById(moderationId);
        if (moderation != null) {
            moderation.setDecision(finalDecision);
            moderation.setManualReason(reviewNote);
            moderation.setReviewerId(reviewerId);
            moderation.setStatus("APPROVED".equals(finalDecision) ? "APPROVED" : "REJECTED");
            moderation.setReviewedAt(new Date());
            moderationMapper.update(moderation);
        }
    }

    private ModerationResultDTO convertToDTO(ContentModeration moderation) {
        List<String> violations = new ArrayList<>();
        try {
            violations = objectMapper.readValue(moderation.getViolations(), new TypeReference<List<String>>() {});
        } catch (Exception e) {
            violations = Arrays.asList("解析失败");
        }

        return ModerationResultDTO.builder()
                .id(moderation.getId())
                .contentId(moderation.getContentId())
                .contentType(moderation.getContentType())
                .decision(moderation.getDecision())
                .confidence(moderation.getConfidence())
                .violations(violations)
                .aiReason(moderation.getAiReason())
                .manualReason(moderation.getManualReason())
                .status(moderation.getStatus())
                .reviewerId(moderation.getReviewerId())
                .createdAt(moderation.getCreatedAt())
                .reviewedAt(moderation.getReviewedAt())
                .build();
    }
}