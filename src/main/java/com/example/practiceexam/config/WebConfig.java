package com.example.practiceexam.config;

import com.example.practiceexam.argument.SharedUserArgumentResolver;
import com.example.practiceexam.interceptor.TokenHandlerInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * 开放跨域
 */
@Order(0)
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        LOGGER.info("######## 应用程序配置跨域... ########");
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS");
    }

    @Bean
    public TokenHandlerInterceptor tokenHandlerInterceptor() {
        return new TokenHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOGGER.info("######## 配置拦截器... ");
        TokenHandlerInterceptor interceptor = tokenHandlerInterceptor();
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor).addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns(TokenHandlerInterceptor.URL_EXCLUDE_LIST);
    }

    @Bean
    public SharedUserArgumentResolver sharedUserArgumentResolver() {
        return new SharedUserArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(sharedUserArgumentResolver());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        // 日期格式转换
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        objectMapper.setDateFormat(dateFormat);
        // Long类型转String类型
        // 不显示为null的字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule simpleModule = new SimpleModule();
        // Long
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        // long
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        //放到第一个
        converters.add(0, jackson2HttpMessageConverter);
    }
}
