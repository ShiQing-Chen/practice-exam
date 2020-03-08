package com.example.common.alioss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.example.common.exception.UninitializedException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * 阿里云存储基本操作方法
 * @author ShiQing_Chen   2019/11/27  23:05
 */
public class AliOssConfig {
    private static final Logger logger = LoggerFactory.getLogger(AliOssConfig.class);

    private final static String FILE_URL = "https://farmeryun.oss-cn-beijing.aliyuncs.com/";
    /**
     * 是否已初始化
     */
    private boolean inited = false;
    /**
     * 地域节点
     */
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    /**
     * 存储空间
     */
    private String bucketName;

    /**
     * 创建存储空间Bucket
     * @param bucketName="farmeryun"
     */
    public boolean createBucket(String bucketName) {
        if (StringUtils.isBlank(bucketName)) {
            return false;
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建存储空间。
        ossClient.createBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
        return true;
    }

    /**
     * 文件上传
     * @param file       文件 如: J:/projects/cloud/system/system-monitor/123.xls
     * @param objectName 存储中的名称 如: test/000fc6f7-b588-40fc-b6b2-5d980ff7ff4c.xls
     * @return 成功/失败
     */
    public String putObject(final File file, final String objectName) {
        String resultUrl = FILE_URL + objectName;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, objectName, new FileInputStream(file));
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            logger.error("######AliOss上传文件失败：", e);
        }
        return resultUrl;
    }

    /**
     * 简单列举文件,默认列举100个文件。
     * @param keyPrefix 如果不设置KeyPrefix，则列举存储空间下所有的文件。KeyPrefix，则列举包含指定前缀的文件。
     * @return object的名称
     */
    public List<String> listObjects(String keyPrefix) {
        List<String> objectKeyListing = Lists.newArrayList();
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 列举文件。 如果不设置KeyPrefix，则列举存储空间下所有的文件。KeyPrefix，则列举包含指定前缀的文件。
            ObjectListing objectListing;
            if (StringUtils.isNotBlank(keyPrefix)) {
                objectListing = ossClient.listObjects(bucketName, keyPrefix);
            } else {
                objectListing = ossClient.listObjects(bucketName);
            }
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                objectKeyListing.add(s.getKey());
            }
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            logger.error("######AliOss列举文件失败：", e);
        }
        return objectKeyListing;
    }

    /**
     * 删除单个文件
     * @param objectName 文件夹名称
     * @return 成功/失败
     */
    public boolean deleteObject(String objectName) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            ossClient.deleteObject(bucketName, objectName);
            // 关闭OSSClient。
            ossClient.shutdown();
            return true;
        } catch (Exception e) {
            logger.error("######AliOss删除文件失败：", e);
        }
        return false;
    }

    /**
     * 批量删除文件
     * @param keys key等同于ObjectName，表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
     * @return 成功/失败
     */
    public boolean deleteObjects(List<String> keys) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            logger.info("######AliOss批量删除文件：" + String.join(",", deletedObjects));
            // 关闭OSSClient。
            ossClient.shutdown();
            return true;
        } catch (Exception e) {
            logger.error("######AliOss批量删除文件失败：", e);
        }
        return false;
    }

    private static final class SingletonHolder {
        private static final AliOssConfig INSTANCE = new AliOssConfig();
        private SingletonHolder() {
            //default
        }
    }

    public static AliOssConfig getInstance() {
        if (!AliOssConfig.SingletonHolder.INSTANCE.inited) {
            throw new UninitializedException("have not been initialized, need init aliOss of setting.");
        }
        return AliOssConfig.SingletonHolder.INSTANCE;
    }

    /**
     * 构造函数
     */
    private AliOssConfig() {
        //阻止外部初始化
    }

    /**
     * 初始化
     * @param endpoint        地域节点
     * @param accessKeyId     账号AccessKey
     * @param accessKeySecret 账号accessKeySecret
     * @param bucketName      存储空间
     */
    public static void init(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        if (StringUtils.isNotBlank(endpoint) && StringUtils.isNotBlank(accessKeyId)
                && StringUtils.isNotBlank(accessKeySecret) && StringUtils.isNotBlank(bucketName)) {
            AliOssConfig.SingletonHolder.INSTANCE.endpoint = endpoint;
            AliOssConfig.SingletonHolder.INSTANCE.accessKeyId = accessKeyId;
            AliOssConfig.SingletonHolder.INSTANCE.accessKeySecret = accessKeySecret;
            AliOssConfig.SingletonHolder.INSTANCE.bucketName = bucketName;
            AliOssConfig.SingletonHolder.INSTANCE.inited = true;
        }
    }
}
