package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.entity.Supplier;
import com.xymzsfxy.backend.mapper.SupplierMapper;
import com.xymzsfxy.backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public Supplier findBySupplierName(String name) {
        return supplierMapper.findBySupplierName(name);
    }

    @Override
    public void add(String name, String contact, String address) {
        Random random = new Random();
        // 生成8.0到10.0之间的随机数，保留一位小数
        BigDecimal rating = new BigDecimal(random.nextDouble(2) + 8)
                .setScale(1, RoundingMode.HALF_UP);
        supplierMapper.add(name,contact,address,rating);
    }


    @Override
    public List<Supplier> getAll() {
        return supplierMapper.getAll();
    }

    @Override
    public void delete(Long id) {
        supplierMapper.delete(id);
    }

    @Override
    public void update(Long id, String name, String address, String contact) {
        supplierMapper.update(id, name, address, contact);
    }

    @Override
    public Long getTotalCount() {
        return supplierMapper.getTotalCount();
    }

    @Override
    public List<Supplier> fuzzySearchSuppliers(String query, int offset, Integer size) {
        return supplierMapper.fuzzyFindByName(query, offset, size);
    }
    @Override
    public Long fuzzyCountSuppliers(String query) {
        return supplierMapper.fuzzyCountByName(query);
    }
}
