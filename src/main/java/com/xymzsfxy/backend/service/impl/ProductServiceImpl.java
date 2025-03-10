package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.entity.Product;
import com.xymzsfxy.backend.mapper.ProductMapper;
import com.xymzsfxy.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void add(String name, String category, String description,String imageUrl) {
        productMapper.add(name,category,description,imageUrl);
    }

    @Override
    public void update(Long id, String name, String category, String description, String imageUrl) {
        productMapper.update(id,name,category,description,imageUrl);
    }

    @Override
    public void delete(Long id) {
        productMapper.delete(id);
    }

    @Override
    public Product FindByProductId(Long id) {
        Product product = productMapper.FindByProductId(id);
        return product;
    }

    @Override
    public List<Product> get(Integer page, Integer size) {
        // 计算偏移量
        Integer offset = (page - 1) * size;
        List<Product> product = productMapper.get(offset, size);
        return product;
    }

    @Override
    public Long getTotalCount() {
        return productMapper.getTotalCount();
    }

    @Override
    public List<Product> FindByProductName(String name, Integer page, Integer size) {
        // 计算偏移量
        Integer offset = (page - 1) * size;
        return productMapper.FindByProductName(name,offset,size);
    }

    @Override
    public Long getNameCount(String name) {
        return productMapper.getNameCount(name);
    }
}
