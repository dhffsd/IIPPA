package com.xymzsfxy.backend.controller.admin;

import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.ProductService;
import com.xymzsfxy.backend.service.SupplierService;
import com.xymzsfxy.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/info")
public class InforController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;

    // 获取商品数量
    @GetMapping("/getProductTotal")
    public Result<Long> getProductTotal(){
        Long totalCount = productService.getTotalCount();
        return Result.success(totalCount);
    }
    // 获取供应商数量
    @GetMapping("/getSupplierTotal")
    public Result<Long> getSupplierTotal(){
        Long totalCount = supplierService.getTotalCount();
        return Result.success(totalCount);
    }

    // 获取用户数量
    @GetMapping("/getUserTotal")
    public Result<Long> getUserTotal(){
        Long totalCount = userService.getInfoCount();
        return Result.success(totalCount);
    }
}
