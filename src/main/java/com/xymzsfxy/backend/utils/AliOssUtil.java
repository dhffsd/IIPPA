package com.xymzsfxy.backend.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class AliOssUtil {

    private static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com"; // 更改为你的Endpoint

    private static final String BUCKET_NAME = "bucket-iipa"; // 更改为你的Bucket名称

    private static OSS ossClient;

    // 初始化OSS客户端
    public static void init() {
        ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    // 上传文件到OSS
    public static void uploadFile(String objectName, File file) {
        ossClient.putObject(new PutObjectRequest(BUCKET_NAME, objectName, file));
    }

    // 删除OSS上的文件
    public static void deleteFile(String objectName) {
        ossClient.deleteObject(BUCKET_NAME, objectName);
    }

    // 获取文件URL
    public static URL getUrl(String objectName, int expires) {
        // 设置URL过期时间为expires秒后
        Date expiration = new Date(System.currentTimeMillis() + expires * 1000L);
        return ossClient.generatePresignedUrl(BUCKET_NAME, objectName,expiration);
    }

    // 列出Bucket中的所有对象
    public static List<OSSObjectSummary> listAllObjects() {
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(BUCKET_NAME));
        List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        while (true) {
            // 如果有下一批，继续获取
            if (objectListing.isTruncated()) {
                objectListing = ossClient.listObjects(String.valueOf(objectListing));
                objectSummaries.addAll(objectListing.getObjectSummaries());
            } else {
                break;
            }
        }
        return objectSummaries;
    }


    // 关闭OSS客户端
    public static void shutdown() {
        ossClient.shutdown();
    }
}