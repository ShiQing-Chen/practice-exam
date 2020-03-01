package com.example.practiceexam.updown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class ShutdownService implements DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownService.class);

    @Override
    public void destroy() {
        LOGGER.info("######## 应用程序正在关闭... ########");
        LOGGER.info("######## 应用程序正在关闭... ########");
        LOGGER.info("######## 应用程序正在关闭... ########");
    }
}
