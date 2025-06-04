package com.xymzsfxy.backend.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryService {
    // 获取所有的价格数据
    List<History> findAll();

    // 每天凌晨准时更新数据
    void batchUpdate(List<History> priceHistories);



}
