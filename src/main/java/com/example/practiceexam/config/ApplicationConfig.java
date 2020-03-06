package com.example.practiceexam.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.MultipartConfigElement;

@Configuration
public class ApplicationConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //线程池所使用的缓冲队列
        threadPoolTaskExecutor.setQueueCapacity(200);
        //线程池维护线程的最少数量
        threadPoolTaskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        //线程池维护线程的最大数量
        threadPoolTaskExecutor.setMaxPoolSize(1000);
        //线程池维护线程所允许的空闲时间
        threadPoolTaskExecutor.setKeepAliveSeconds(30000);
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }

    /**
     * 文件上传临时路径
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String tmpPath = System.getProperty("user.dir");
        LOGGER.info("######## 临时目录：{}", tmpPath);
        factory.setLocation(tmpPath);
        return factory.createMultipartConfig();
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() throws PropertyVetoException {
//        return new BCryptPasswordEncoder();
//    }
}
