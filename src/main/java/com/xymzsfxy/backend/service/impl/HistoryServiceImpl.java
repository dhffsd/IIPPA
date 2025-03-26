package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.entity.History;
import com.xymzsfxy.backend.mapper.HistoryMapper;
import com.xymzsfxy.backend.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public List<History> findAll() {
        return historyMapper.findAll();
    }

    @Override
    public void batchUpdate(List<History> priceHistories) {
        historyMapper.batchUpdate(priceHistories);
    }
}
