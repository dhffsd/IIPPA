package com.xymzsfxy.backend.controller.admin;


import com.xymzsfxy.backend.dto.HistoryDTO;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.HistoryService;
import com.xymzsfxy.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/price")
public class PriceController {

    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProductService productService;

    // 获取所有的价格数据
    @GetMapping("/get")
    public Result<List<HistoryDTO>> getAll() {
        List<History> all = historyService.findAll();
        List<HistoryDTO> historyDTOList = new ArrayList<>();

        for (History history : all) {
            Product product = productService.FindByProductId(history.getProductId());
            String productName = product.getName();
            HistoryDTO historyDTO = new HistoryDTO(history, productName);
            historyDTOList.add(historyDTO);
        }

        return Result.success(historyDTOList);
    }

    // 根据商品名称获取数据



}
