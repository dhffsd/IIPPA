package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.entity.Product;
import com.xymzsfxy.backend.mapper.ProductMapper;
import com.xymzsfxy.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
