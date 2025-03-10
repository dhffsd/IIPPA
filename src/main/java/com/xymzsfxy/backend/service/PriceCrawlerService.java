package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.config.CrawlerConfig;
import com.xymzsfxy.backend.entity.PriceHistory;
import com.xymzsfxy.backend.entity.Product;
import com.xymzsfxy.backend.repository.PriceHistoryRepository;
import com.xymzsfxy.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceCrawlerService {


    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;


    private final CrawlerConfig crawlerConfig;



    private static final int TIMEOUT_MS = 20_000;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";

    // 修改 crawlPrices 方法，从 crawlerConfig 获取 sources
    @Async("crawlerTaskExecutor")
    public void crawlPrices(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(
                product -> crawlerConfig.getSources().forEach((source, url) -> processSource(product, source, url)),
                () -> log.warn("商品不存在: ID={}", productId)
        );
    }

    private void processSource(Product product, String sourceName, String urlTemplate) {
        try {
            final String targetUrl = buildTargetUrl(product, urlTemplate);
            final Document document = fetchDocument(targetUrl);
            final BigDecimal price = extractPrice(document, sourceName);

            log.info("正在抓取页面: {}", targetUrl); // 增加请求日志
            log.debug("页面内容片段:\n{}", document.html().substring(0, 500)); // 输出HTML片段
            savePriceData(product, sourceName, price);
            log.debug("价格抓取成功: {} | {} | {}", sourceName, product.getId(), price);
        } catch (IOException e) {
            log.error("[{}] 网络请求异常: {}", sourceName, e.getMessage());
        } catch (Exception e) {
            log.error("[{}] 数据处理异常: {}", sourceName, e.getMessage(), e);
        }
    }

    private String buildTargetUrl(Product product, String urlTemplate) {
        return String.format(urlTemplate, product.getExternalId());
    }

    public Document fetchDocument(String url) throws IOException {
        // 添加随机延迟（1-3秒）
        try {
            Thread.sleep(1000 + new Random().nextInt(2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 添加动态参数避免缓存
        String timestamp = String.valueOf(System.currentTimeMillis());
        return Jsoup.connect(url + "&_t=" + timestamp)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header("Referer", "https://www.tmall.com/")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36 Edg/134.0.0.0")
                .cookie("cookie","lid=tb39355136; arms_uid=1df74d84-8349-476b-82f7-07f330d1edfd; xlly_s=1; isg=BEREM1hA__p7BUu2r6StdvW2FcI2XWjHfFoQXV7l0I_SieRThm04V3o6yyFRkaAf; cna=WG9UIByKcSsCAXW94rNovoOm; dnk=; sgcookie=E100apxwLJhAJgBi3OxbDr4xnSOWsPqzsabycF0zYRvoP37SfYSzjHsIkJm1ateZLSbNaKWi3cc8okcy%2FCjhBt6XN%2F30jIBeOWx%2B2PRcqvqmha%2BNT5a5Sr7kkA1H8GkH3zSo; t=4812e216e1886df955234beabf9e0686; tracknick=tb39355136; lgc=; sn=; _tb_token_=e75b3499b63e8; cookie2=139e3f2bbfb549046d063db7d3c9fa3f; _nk_=; mtop_partitioned_detect=1; _m_h5_tk=b2e407d1e7d757ea71c10a0e11759c38_1741574657427; _m_h5_tk_enc=cc9bdafe229bef49f14b0f1856795f93; tfstk=g8gtK5qWGpvgUE9JQV-hoqiUS5RHZHcaJAl5o-2GcvHKUYQD_cDmvSMnHr4mGdkxkJMOocciGsFjKPZqj-2gHrhqeBvkrUcZ_rzBELYk2xhkFzWbhr60OeN0k51gjUBS_rzXE6bXqUhZFw4jdxaX9yNYZZabfGwB9SVflrw_c9sQNJabhxZ_AMN0i1_1fGTB9SyQhrajhyOLivu56-f_HZn7GYEqweWNWN3L6lefoJQYi2buj8GgpZtnP5QaFfwdlZ2GNhGE9fvOzX4ZOYFoIe_tebuEpWMWkpUqLmMbMvTPnPooQ4rKT3_ItPeQV7gBGawmWDc7pzCX5XUTBuggfUdrFXhjqk3HNUzT5Ri07jfJIX3tIfuKissQWPmL2VUWzOwmxb3YMqJ2JYhr8vFIFEp14Juoyf3cE8FcfBdd0ir_TwRIjrAIdIk898AhxiS4xWPLEBdd0ir_TWektejV0kVF.")
                .timeout(30_000) // 延长超时时间
                .get();
    }

    // 修改 extractPrice 方法，从 crawlerConfig 获取选择器
    public BigDecimal extractPrice(Document document, String sourceName) {
        // 多选择器回退机制
        String[] fallbackSelectors = {
                "span[class^='unit--'] + span[class^='text--']", // 主选择器
                "div[style*='color: rgb(255, 79, 0)'] span.text--", // 通过颜色特征定位
                "span.text--" // 兜底选择器
        };

        for (String selector : fallbackSelectors) {
            Element element = document.selectFirst(selector);
            if (element != null) {
                return parsePrice(element.text());
            }
        }

        log.error("所有备用选择器均未匹配: {}", String.join(", ", fallbackSelectors));
        CrawlerConfig.SourceConfig sourceConfig = crawlerConfig.getSelectors().get(sourceName);
        if (sourceConfig == null) {
            log.error("未找到源 [{}] 的选择器配置", sourceName);
            return null;
        }
        CrawlerConfig.SourceConfig.PriceConfig priceConfig = sourceConfig.getPrice();
        if (priceConfig == null) {
            log.error("未找到源 [{}] 的价格选择器配置", sourceName);
            return null;
        }
        String selector = priceConfig.getSelector();
        if (selector == null || selector.isEmpty()) {
            log.error("源 [{}] 的价格选择器为空", sourceName);
            return null;
        }
        org.jsoup.nodes.Element element = document.selectFirst(selector);
        if (element == null) {
            log.error("根据选择器 [{}] 未找到元素，源名称: {}", selector, sourceName);
            return null;
        }
        String priceText = element.text();
        // 应用转换规则
        String transformedText = priceText.replaceAll("[^\\d.]", "");
        return parsePrice(transformedText);
    }

    private BigDecimal parsePrice(String priceText) {
        String numericString = priceText.replaceAll("[^\\d.,]", "");
        return new BigDecimal(numericString.replace(",", ""));
    }

    @Transactional
    protected void savePriceData(Product product, String source, BigDecimal price) {
        persistPriceHistory(product, source, price);
        updateProductPrice(product, price);
    }

    private void persistPriceHistory(Product product, String source, BigDecimal price) {
        PriceHistory history = PriceHistory.builder()
                .productId(product.getId())
                .source(source)
                .price(price)
                .crawlTime(LocalDateTime.now())
                .build();
        priceHistoryRepository.save(history);
    }

    private void updateProductPrice(Product product, BigDecimal price) {
        product.setLatestPrice(price);
        product.setUpdatedTime(LocalDateTime.now());
        productRepository.save(product);
    }
}