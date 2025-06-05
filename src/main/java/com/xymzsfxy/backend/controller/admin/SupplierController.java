package com.xymzsfxy.backend.controller.admin;

import com.xymzsfxy.backend.entity.Supplier;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    // 增加供应商
    @PostMapping("/add")
    public Result add(Supplier supplier){
        // 查询供应商是否存在
        Supplier bySupplierName = supplierService.findBySupplierName(supplier.getName());
        if(bySupplierName == null){
            // 增加供应商
            supplierService.add(supplier.getName(),supplier.getContact(),supplier.getAddress());
            return Result.success();
        }

        return Result.badRequest("供应商已存在");
    }

    // 获取供应商
    @GetMapping("/getAll")
    public Result<List<Supplier>> getAll(){
        List<Supplier> all = supplierService.getAll();
        return Result.success(all);
    }

    // 删除供应商
    @DeleteMapping("/delete")
    public Result delete(Supplier supplier){
        supplierService.delete(supplier.getId());
        return Result.success();
    }

    // 修改供应商
    @PutMapping("/update")
    public Result update(Supplier supplier){
        supplierService.update(supplier.getId(), supplier.getName(), supplier.getAddress(), supplier.getContact());
        return Result.success();
    }

    // 根据供应商名称查找数据
    @GetMapping("/getName")
    public Result<Supplier> getName(Supplier supplier){
        // 根据名字查找
        Supplier bySupplierName = supplierService.findBySupplierName(supplier.getName());
        return Result.success(bySupplierName);
    }

}
