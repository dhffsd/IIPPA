package com.xymzsfxy.backend.controller.admin;

import com.xymzsfxy.backend.entity.Product;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    // 添加商品
    @PostMapping("/add")
    public Result add(String name,String category,String description,String imageUrl){
        productService.add(name,category,description,imageUrl);
        return Result.success();
    }
    // 修改商品
    @PutMapping("/update")
    public Result update(Long id,String name,String category,String description,String imageUrl){
        productService.update(id,name,category,description,imageUrl);
        return Result.success();
    }

    // 删除商品
    @DeleteMapping("/delete")
    public Result delete(Long id){
        // 查询商品是否存在
        Product product = productService.FindByProductId(id);
        if(product == null){
            return Result.error("商品不存在");
        }
        // 删除商品
        productService.delete(id);
        return Result.success();
    }

    // 获取商品
    @GetMapping("/get")
    public Result<Product> get(Long id){
        // 查询商品
        Product product = productService.FindByProductId(id);
        if(product == null){
            return Result.error("商品不存在");
        }else {
            return Result.success(product);
        }

    }
}
