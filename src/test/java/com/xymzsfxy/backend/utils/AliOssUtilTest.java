package com.xymzsfxy.backend.utils;


import com.aliyun.oss.model.OSSObjectSummary;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.net.URL;
import java.util.List;

@Slf4j
@SpringBootTest
class Application {

    // 上传文件
    @Test
    void contextLoads() {
        AliOssUtil.init();

        // 上传文件
        File file = new File("C:\\Users\\ESD\\Pictures\\logo.png");
        String name = file.getName();
        AliOssUtil.uploadFile(name, file);

        // 关闭OSS客户端
        AliOssUtil.shutdown();
    }


    // 删除文件
    @Test
    void deleteFile() {
        AliOssUtil.init();

        // 删除文件
        AliOssUtil.deleteFile("AccessKey.csv");

        // 关闭OSS客户端
        AliOssUtil.shutdown();
    }

    @Test
    void getUrl() {
        AliOssUtil.init();

        // 获取文件URL
        URL url = AliOssUtil.getUrl("1.jpg", 3600);// 3600 seconds = 1 hour
        if (url != null) {
            System.out.println("文件URL: " + url);
        } else {
            System.out.println("无法获取文件URL");
        }

        // 关闭OSS客户端
        AliOssUtil.shutdown();
    }

    @Test
    void listObjects() {
        AliOssUtil.init();

        // 列出所有文件
        List<OSSObjectSummary> summaries = AliOssUtil.listAllObjects();
        for (OSSObjectSummary summary : summaries) {
            System.out.println(" - " + summary.getKey() + "  " +
                    "(size = " + summary.getSize() + ")");
        }

        // 关闭OSS客户端
        AliOssUtil.shutdown();
    }


}