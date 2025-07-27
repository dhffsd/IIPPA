package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.dto.ModerationRequestDTO;
import com.xymzsfxy.backend.dto.ModerationResultDTO;

import java.util.List;

public interface ContentModerationService {
    /**
     * 审核文章内容
     */
    ModerationResultDTO moderateArticle(ModerationRequestDTO request);

    /**
     * 审核评论内容
     */
    ModerationResultDTO moderateComment(ModerationRequestDTO request);

    /**
     * 批量审核内容
     */
    List<ModerationResultDTO> batchModerate(List<ModerationRequestDTO> requests);

    /**
     * 获取审核历史
     */
    List<ModerationResultDTO> getModerationHistory(String contentId, String contentType);

    /**
     * 人工复审
     */
    void manualReview(Long moderationId, String finalDecision, Long reviewerId, String reviewNote);
}
