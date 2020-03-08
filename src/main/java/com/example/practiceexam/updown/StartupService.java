package com.example.practiceexam.updown;

import com.example.common.alioss.AliOssConfig;
import com.example.common.snowflake.SnowflakeIdWorker;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StartupService implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartupService.class);

    private final Environment environment;

    private final BuildProperties buildProperties;

    @Value("${server.port}")
    private Integer serverPort;

    @Value("${snowflake.data-center-id}")
    private Integer dataCenterId;

    @Value("${snowflake.worker-id}")
    private Integer workerId;

    @Value("${alioss.endpoint}")
    private String endpoint;

    @Value("${alioss.accessKeyId}")
    private String accessKeyId;

    @Value("${alioss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${alioss.bucketName}")
    private String bucketName;

    @Autowired
    public StartupService(Environment environment, BuildProperties buildProperties) {
        this.environment = environment;
        this.buildProperties = buildProperties;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void afterPropertiesSet() {
        LOGGER.info("######## 应用程序正在启动... ########");
        LOGGER.info("######## 应用程序正在启动... ########");
        LOGGER.info("######## 应用程序正在启动... ########");
        LOGGER.info("######## 运行端口: {} ", serverPort);
        LOGGER.info("######## 应用名称: {} ", buildProperties.getName());
        LOGGER.info("######## 程序版本: {} ", buildProperties.getVersion());
        DateTime dt = new DateTime(buildProperties.getTime().toEpochMilli());
        LOGGER.info("######## 构建时间: {} ", dt.toString("yyyy-MM-dd HH:mm:ss"));
        LOGGER.info("######## 使用配置文件: {} ", Arrays.asList(environment.getActiveProfiles()));

        //初始化ID 生成器
        SnowflakeIdWorker.init(dataCenterId,workerId);
        //初始化阿里云对象存储
        AliOssConfig.init(endpoint, accessKeyId, accessKeySecret, bucketName);
    }
}
