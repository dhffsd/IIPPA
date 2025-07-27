package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // 分页获取商品方法
    List<Product> get(Integer page, Integer size);

    // 获取商品总数方法
    Long getTotalCount();

    // 根据名称获取商品
    List<Product> FindByProductName(String name,Integer page,Integer size);

    // 获取模糊查询的数量
    Long getNameCount(String name);

    List<Product> fuzzySearchProducts(String query, int offset, Integer size);
    Long fuzzyCountProducts(String query);
}
