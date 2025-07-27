package com.xymzsfxy.backend.controller.admin;

import com.xymzsfxy.backend.dto.ModerationRequestDTO;
import com.xymzsfxy.backend.dto.ModerationResultDTO;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.ContentModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/moderation")
public class ModerationController {
    @Autowired
    private ContentModerationService moderationService;

    /**
     * 审核文章
     */
    @PostMapping("/article")
    public Result<ModerationResultDTO> moderateArticle(@RequestBody ModerationRequestDTO request) {
        ModerationResultDTO result = moderationService.moderateArticle(request);
        return Result.success(result);
    }

    /**
     * 审核评论
     */
    @PostMapping("/comment")
    public Result<ModerationResultDTO> moderateComment(@RequestBody ModerationRequestDTO request) {
        ModerationResultDTO result = moderationService.moderateComment(request);
        return Result.success(result);
    }

    /**
     * 批量审核
     */
    @PostMapping("/batch")
    public Result<List<ModerationResultDTO>> batchModerate(@RequestBody List<ModerationRequestDTO> requests) {
        List<ModerationResultDTO> results = moderationService.batchModerate(requests);
        return Result.success(results);
    }

    /**
     * 获取审核历史
     */
    @GetMapping("/history")
    public Result<List<ModerationResultDTO>> getModerationHistory(
            @RequestParam String contentId,
            @RequestParam String contentType) {
        List<ModerationResultDTO> history = moderationService.getModerationHistory(contentId, contentType);
        return Result.success(history);
    }

    /**
     * 人工复审
     */
    @PostMapping("/review")
    public Result<?> manualReview(
            @RequestParam Long moderationId,
            @RequestParam String finalDecision,
            @RequestParam Long reviewerId,
            @RequestParam(required = false) String reviewNote) {
        moderationService.manualReview(moderationId, finalDecision, reviewerId, reviewNote);
        return Result.success();
    }
}
