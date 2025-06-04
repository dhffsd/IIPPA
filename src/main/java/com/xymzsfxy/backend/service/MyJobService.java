package com.xymzsfxy.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class MyJobService {
    @Autowired
    private HistoryService historyService;

    // 0 0 0 * * * 每天凌晨更新
    // */5 * * * * * 每隔5秒更新
    @Scheduled(cron = "0 0 0 * * *")
    public void updatePriceHistory() {
        List<History> priceHistories = historyService.findAll();
        Random random = new Random();

        priceHistories.forEach(history -> {
            // 生成新价格并保留两位小数
            BigDecimal newPrice = new BigDecimal(random.nextDouble() * 1000).setScale(2, RoundingMode.HALF_UP);
            history.setPrice(newPrice);

            // 生成物流费用并保留两位小数
            BigDecimal logisticsCost = new BigDecimal(random.nextDouble() * 100).setScale(2, RoundingMode.HALF_UP);
            history.setLogisticsCost(logisticsCost);

            // 计算综合成本并保留两位小数
            BigDecimal totalCost = newPrice.add(logisticsCost).setScale(2, RoundingMode.HALF_UP);
            history.setTotalCost(totalCost);

            // 生成评分并保留一位小数
            BigDecimal rating = new BigDecimal(4 + random.nextDouble()).setScale(1, RoundingMode.HALF_UP);
            history.setRating(rating);

            history.setCrawlTime(LocalDateTime.now());

            // 比较新价格与最高价和最低价
            BigDecimal currentMaxPrice = history.getMaxPrice();
            BigDecimal currentMinPrice = history.getMinPrice();

            // 如果当前最高价为null或者新价格大于当前最高价，并且新价格大于0，则更新最高价
            if ((currentMaxPrice == null || newPrice.compareTo(currentMaxPrice) > 0) && newPrice.compareTo(BigDecimal.ZERO) > 0) {
                history.setMaxPrice(newPrice);
            }

            // 如果当前最低价为null或者新价格小于当前最低价，并且新价格大于0，则更新最低价
            if ((currentMinPrice == null || newPrice.compareTo(currentMinPrice) < 0) && newPrice.compareTo(BigDecimal.ZERO) > 0) {
                history.setMinPrice(newPrice);
            }

            String[] sources = {"天猫", "淘宝"};
            int index = random.nextInt(sources.length);
            history.setSource(sources[index]);
        });

        historyService.batchUpdate(priceHistories); // 批量更新
    }
}