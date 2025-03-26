package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.entity.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {
    // 根据供应商名称查询
    Supplier findBySupplierName(String name);

    // 增加供应商
    void add(String name, String contact, String address);


    // 获取所有的供应商
    List<Supplier> getAll();

    // 删除供应商
    void delete(Long id);

    // 修改供应商
    void update(Long id, String name, String address, String contact);

    // 获取供应商数量
    Long getTotalCount();
}
