package com.example.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ShiQing_Chen   2019/11/30  20:24
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
        //default
    }

    /**
     * 获取文件后缀名
     * @param filename 文件名称
     * @return 后缀名 例如.jpg
     */
    public static String getFileExt(final String filename) {
        try {
            return filename.substring(filename.lastIndexOf('.')).toLowerCase();
        } catch (Exception e) {
            logger.warn("获取文件后缀名称错误", e);
        }
        return "";
    }
}
