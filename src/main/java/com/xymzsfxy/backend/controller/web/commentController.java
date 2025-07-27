package com.xymzsfxy.backend.controller.web;
// 评论控制器

import com.xymzsfxy.backend.dto.CommentCreateDTO;
import com.xymzsfxy.backend.dto.CommentListDTO;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/comment")
public class commentController {
    @Autowired
    private CommentService commentService;



    // 增加评论
    @PostMapping("/create")
    public Result commentCreate(@RequestBody CommentCreateDTO commentCreateDTO){
        boolean isTrue = commentService.commentCreate(commentCreateDTO);
        if(isTrue){
            return Result.success();
        }else {
            return Result.badRequest("评论内容不符合平台规范，请修改后重新发布");
        }

    }


    /**
     * 查询评论列表（分页）
     */
    @GetMapping("/list")
    public Result<Result.PageData<List<CommentListDTO>>> getCommentList(
            @RequestParam String targetType,
            @RequestParam String targetId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        // 参数校验
        if (page < 1 || size < 1) {
            return Result.badRequest("页码或每页数量不合法");
        }

        // 查询评论列表（含分页）
        List<CommentListDTO> commentList = commentService.getCommentList(targetType, targetId, page, size);
        Long total = commentService.getCommentTotal(targetType, targetId);

        // 封装返回数据
        return Result.successWithPage(total,commentList);
    }


    // 查询评论


}
