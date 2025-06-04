package com.xymzsfxy.backend.controller.admin;

import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    // 添加商品
    @PostMapping("/add")
    public Result add(Product product){
        productService.add(product.getName(),product.getCategory(),product.getDescription(),product.getImageUrl());
        return Result.success();
    }
    // 修改商品
    @PutMapping("/update")
    public Result update(Product product){
        productService.update(product.getId(),product.getName(),product.getCategory(),product.getDescription(),product.getImageUrl());
        return Result.success();
    }

    // 删除商品
    @DeleteMapping("/delete")
    public Result delete(@RequestParam("id") Long id){
        // 查询商品是否存在
        Product product = productService.FindByProductId(id);
        if(product == null){
            return Result.notFound("商品");
        }
        // 删除商品
        productService.delete(id);
        return Result.success();
    }

    // 获取全部商品
    @GetMapping("/getAll")
    public Result<Result.PageData<List<Product>>> get(Integer page,Integer size){
        // 查询商品
        List<Product> product = productService.get(page,size);
        Long total = productService.getTotalCount();
        return Result.successWithPage(total,product);

    }

    // 根据id获取商品
    @GetMapping("/getId")
    public Result<Product> getId(Product product){
        Product id = productService.FindByProductId(product.getId());
        return Result.success(id);
    }

    // 根据名称获取商品
    @GetMapping("/getName")
    public Result<Result.PageData<List<Product>>> getName(Product product,Integer page,Integer size){
        List<Product> productName = productService.FindByProductName(product.getName(),page,size);
        Long total = productService.getNameCount(product.getName());
        return Result.successWithPage(total,productName);
    }


}
