package com.example.practiceexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 由于Security自带一套权限验证，
 * 目前只使用Security的BCryptPasswordEncoder用于密码加密和校验
 * exclude = {SecurityAutoConfiguration.class}
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PracticeExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeExamApplication.class, args);
    }

}
