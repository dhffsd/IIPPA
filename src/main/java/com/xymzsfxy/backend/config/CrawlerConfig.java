package com.xymzsfxy.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "crawler")
public class CrawlerConfig {
    private Map<String, String> sources;
    private Map<String, SourceConfig> selectors;

    // 内部类表示每个数据源的详细配置
    public static class SourceConfig {
        private PriceConfig price;

        public PriceConfig getPrice() {
            return price;
        }

        public void setPrice(PriceConfig price) {
            this.price = price;
        }

        // 内部类表示价格相关配置
        public static class PriceConfig {
            private String selector;
            private String extract;
            private String transform;

            public String getSelector() {
                return selector;
            }

            public void setSelector(String selector) {
                this.selector = selector;
            }

            public String getExtract() {
                return extract;
            }

            public void setExtract(String extract) {
                this.extract = extract;
            }

            public String getTransform() {
                return transform;
            }

            public void setTransform(String transform) {
                this.transform = transform;
            }
        }
    }

    public Map<String, String> getSources() {
        return sources;
    }

    public void setSources(Map<String, String> sources) {
        this.sources = sources;
    }

    public Map<String, SourceConfig> getSelectors() {
        return selectors;
    }

    public void setSelectors(Map<String, SourceConfig> selectors) {
        this.selectors = selectors;
    }
}