package com.example.common.util;


import com.example.common.snowflake.SnowflakeIdWorker;

/**
 * id 生成器
 */
public class IdGeneratorUtils {

    /**
     * 返回雪花id
     * @return long snowflakeId
     */
    public static long getNewId(){
        return SnowflakeIdWorker.getInstance().nextId();
    }
}
