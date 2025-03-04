package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.entity.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    // 添加商品方法
    void add(String name, String category, String description,String imageUrl);

    // 修改商品方法
    void update(Long id, String name, String category, String description, String imageUrl);

    // 删除商品方法
    void delete(Long id);

    // 根据id查询商品方法
    Product FindByProductId(Long id);
}
