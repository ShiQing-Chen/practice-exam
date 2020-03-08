package com.example.common.alioss;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 文件上传工具
 * @author ShiQing_Chen   2019/11/28  0:25
 */
public class AliOssUtil {

    /**
     * 上传文件
     * @param file       文件
     * @param objectName 文件名称
     * @param folder     上传所放目录
     * @return 下载地址
     */
    public static String putObject(final File file, final String objectName, final String folder) {
        if (file == null || StringUtils.isBlank(objectName) || StringUtils.isBlank(folder)) {
            return null;
        }
        return AliOssConfig.getInstance().putObject(file, folder + "/" + objectName);
    }

    /**
     * 删除仓库文件
     * @param objectName 文件名称
     * @return 成功/失败
     */
    public boolean deleteObject(String objectName) {
        if (StringUtils.isNotBlank(objectName)) {
            return AliOssConfig.getInstance().deleteObject(objectName);
        }
        return false;
    }
}
