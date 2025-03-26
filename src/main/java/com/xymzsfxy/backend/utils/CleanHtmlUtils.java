package com.xymzsfxy.backend.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CleanHtmlUtils {
    public static String cleanHtml(String html) {
        if (html == null) return "";

        Document doc = Jsoup.parse(html);
        // 保留换行符
        return doc.text().replaceAll("\\n+", "\n").trim();
    }
}
