//package com.xymzsfxy.backend.service;
//import com.xymzsfxy.backend.config.CrawlerConfig;
//import com.xymzsfxy.backend.entity.PriceHistory;
//import com.xymzsfxy.backend.entity.Product;
//import com.xymzsfxy.backend.repository.PriceHistoryRepository;
//import com.xymzsfxy.backend.repository.ProductRepository;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.test.context.TestPropertySource;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//
//import static org.awaitility.Awaitility.await;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@EnableAsync
//@TestPropertySource(properties = {
//        "crawler.sources.jd=https://item.jd.com/%s",
//        "crawler.selectors.jd=#price",
//        "crawler.sources.taobao=https://item.taobao.com/item.htm?id=%s",
//        "crawler.selectors.taobao=.tb-rmb-num"
//})
//class PriceCrawlerServiceTest {
//
//    @SpyBean
//    private PriceCrawlerService priceCrawlerService;
//
//    @MockBean
//    private ProductRepository productRepository;
//
//    @MockBean
//    private PriceHistoryRepository priceHistoryRepository;
//
//    @SpyBean
//    private CrawlerConfig crawlerConfig;
//
//    @Test
//    void shouldSavePriceWhenCrawlSuccess() throws Exception {
//        // 模拟产品数据
//        Product mockProduct = new Product();
//        mockProduct.setId(1L);
//        mockProduct.setExternalId("123");
//
//        // 模拟 CrawlerConfig
//        Map<String, String> sources = new HashMap<>();
//        sources.put("jd", "https://item.jd.com/%s");
//        Map<String, String> selectors = new HashMap<>();
//        selectors.put("jd", "#price");
//        when(crawlerConfig.getSources()).thenReturn(sources);
//        when(crawlerConfig.getSelectors()).thenReturn(selectors);
//
//        // 模拟 HTML 文档
//        String html = "<html><span id='price'>299.00</span></html>";
//        Document mockDocument = Jsoup.parse(html);
//
//        // 配置 Mock 行为
//        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
//        doReturn(mockDocument).when(priceCrawlerService).fetchDocument(anyString());
//
//        // 执行测试方法
//        priceCrawlerService.crawlPrices(1L);
//
//        // 验证异步执行结果
//        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
//            verify(priceHistoryRepository, times(1)).save(any(PriceHistory.class));
//            verify(productRepository, times(1)).save(argThat(p ->
//                    p.getLatestPrice().equals(new BigDecimal("299.00"))
//            ));
//        });
//    }
//
//    @Test
//    void shouldSaveTaobaoPrice() throws Exception {
//        // 模拟淘宝配置
//        Map<String, String> sources = new HashMap<>();
//        sources.put("taobao", "https://item.taobao.com/item.htm?id=%s");
//        Map<String, String> selectors = new HashMap<>();
//        selectors.put("taobao", ".tb-rmb-num");
//        when(crawlerConfig.getSources()).thenReturn(sources);
//        when(crawlerConfig.getSelectors()).thenReturn(selectors);
//
//        // 模拟淘宝商品和HTML
//        Product taobaoProduct = new Product();
//        taobaoProduct.setId(2L);
//        taobaoProduct.setExternalId("456");
//        String html = "<div class='tb-rmb-num'>159.50</div>";
//        Document mockDoc = Jsoup.parse(html);
//
//        when(productRepository.findById(2L)).thenReturn(Optional.of(taobaoProduct));
//        doReturn(mockDoc).when(priceCrawlerService).fetchDocument(anyString());
//
//        priceCrawlerService.crawlPrices(2L);
//
//        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
//            verify(priceHistoryRepository).save(argThat(history ->
//                    history.getSource().equals("taobao") &&
//                            history.getPrice().equals(new BigDecimal("159.50"))
//            ));
//            verify(productRepository, times(1)).save(argThat(p ->
//                    p.getId().equals(2L) &&
//                            p.getLatestPrice().equals(new BigDecimal("159.50"))
//            ));
//        });
//    }
//    @Test
//    void shouldHandleMissingProduct() {
//        when(productRepository.findById(8L)).thenReturn(Optional.empty());
//
//        // 直接调用方法，若抛出异常测试会自动失败
//        priceCrawlerService.crawlPrices(8L);
//
//        verify(priceHistoryRepository, never()).save(any());
//    }
//}