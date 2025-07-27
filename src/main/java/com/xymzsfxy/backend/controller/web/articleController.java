package com.xymzsfxy.backend.controller.web;

import com.xymzsfxy.backend.dto.ArticleCreateDTO;
import com.xymzsfxy.backend.dto.ArticleGetAllDTO;
import com.xymzsfxy.backend.dto.ArticleGetDetailDTO;
import com.xymzsfxy.backend.entity.Articles;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/article")
public class articleController {
    @Autowired
    private ArticleService articleService;


    // 增加文章
    @PostMapping("/create")
    public Result articleCreate(
            @RequestBody ArticleCreateDTO articleCreateDTO,
            @CookieValue(name = "access_token", required = false) String accessToken
    ){
        boolean b = articleService.articleCreate(articleCreateDTO, accessToken);
        if(b){
            return Result.success();
        }else {
            return Result.badRequest("文章内容不符合平台规范，请修改后重新发布");
        }

    }


    // 修改文章

    // 删除文章


    // 根据文章ID获取详情
    @GetMapping("/getId")
    public Result getIdArtrcles(
            @RequestParam() String Id
    ){
        // 根据id获取文章
        ArticleGetDetailDTO articles = articleService.selectById(Id);
        return Result.success(articles);
    }

    // 获取所有文章
    @GetMapping("/getAll")
    public Result<Result.PageData<List<ArticleGetAllDTO>>> getAllArticles(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<ArticleGetAllDTO> allArticles;
        Long total;

        if (category == null || category.isEmpty()) {
            allArticles = articleService.getAllArticles(page, pageSize, null);
            total = articleService.countAll();
        } else {
            allArticles = articleService.getAllArticles(page, pageSize, category);
            total = articleService.countByCategory(category);
        }

        return Result.successWithPage(total,allArticles);


    }
}
