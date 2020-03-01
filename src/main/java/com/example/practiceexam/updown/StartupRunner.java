package com.example.practiceexam.updown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动后干一些事
 */
@Component
@Order(0)
public class StartupRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupRunner.class);


    @Autowired
    public StartupRunner() {

    }

    @Override
    public void run(ApplicationArguments applicationArguments) {

        LOGGER.info("######## 应用程序启动完成!!! ########");
        LOGGER.info("######## 应用程序启动完成!!! ########");
        LOGGER.info("######## 应用程序启动完成!!! ########");
    }
}
