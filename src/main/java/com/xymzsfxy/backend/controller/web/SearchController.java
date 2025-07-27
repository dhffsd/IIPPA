package com.xymzsfxy.backend.controller.web;

import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.dto.ArticleGetAllDTO;
import com.xymzsfxy.backend.entity.Product;
import com.xymzsfxy.backend.entity.Supplier;
import com.xymzsfxy.backend.service.ArticleService;
import com.xymzsfxy.backend.service.ProductService;
import com.xymzsfxy.backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/search")
public class SearchController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/all")
    public Result<Map<String, Object>> searchAll(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        int offset = (page - 1) * size;
        // 文章模糊搜索
        List<ArticleGetAllDTO> articles = articleService.fuzzySearchArticles(query, offset, size);
        Long articleTotal = articleService.fuzzyCountArticles(query);
        // 产品模糊搜索
        List<Product> products = productService.fuzzySearchProducts(query, offset, size);
        Long productTotal = productService.fuzzyCountProducts(query);
        // 供应商模糊搜索
        List<Supplier> suppliers = supplierService.fuzzySearchSuppliers(query, offset, size);
        Long supplierTotal = supplierService.fuzzyCountSuppliers(query);
        Map<String, Object> data = new HashMap<>();
        data.put("articles", Map.of("total", articleTotal, "items", articles));
        data.put("products", Map.of("total", productTotal, "items", products));
        data.put("suppliers", Map.of("total", supplierTotal, "items", suppliers));
        return Result.success(data);
    }
} 