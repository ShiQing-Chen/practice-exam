package com.example.practiceexam.interceptor;

import com.example.common.cache.SharedUser;
import com.example.common.jsckson2.ObjectMapperFactory;
import com.example.common.util.JwtTokenUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.config.OnlineUserManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token检查拦截器
 * @author : HanHongmin
 * @since 0.0.1
 */
public class TokenHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenHandlerInterceptor.class);
    public static final String TOKEN_IN_HEADER = "Authorization";
    private static final String MSG_401 = "{\"success\":false,\"message\":\"无效的 token !\"}";
    private static final String MSG_403 = "{\"success\":false,\"message\":\"没有访问该资源的权限 !\"}";

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    /**
     * 一些不需要登录检查的url 匹配
     */
    public static final ImmutableList<String> URL_EXCLUDE_LIST = ImmutableList.of(
            "/api/user/refreshToken",
            "/api/user/login"
    );

    /**
     * 需要检查的url
     */
    public static final ImmutableList<String> URL_INCLUDE_LIST = ImmutableList.of(
            "/api/**"
    );

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 跨域
        this.crossDomainConfig(response);
        //远程请求放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            // 尚未登录 无效token
            ObjectMapper om = ObjectMapperFactory.getSimpleMapper();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            MessageVo messageVo = new MessageVo(false, "没有token", MSG_401);
            om.writeValue(response.getOutputStream(), messageVo);
            return false;
        }
        Long userId = JwtTokenUtils.getUserId(jwtSecretKey, token);
        if (userId == null) {
            // 尚未登录 无效token
            ObjectMapper om = ObjectMapperFactory.getSimpleMapper();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            MessageVo messageVo = new MessageVo(false, "token失效", MSG_401);
            om.writeValue(response.getOutputStream(), messageVo);
            return false;
        }

        // 获取当前用户
        SharedUser sharedUser = OnlineUserManager.getUserClient(userId);
        if (sharedUser == null) {
            // 401 登录已失效，找不到用户信息
            ObjectMapper om = ObjectMapperFactory.getSimpleMapper();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            MessageVo messageVo = new MessageVo(false, "未登录", MSG_401);
            om.writeValue(response.getOutputStream(), messageVo);
            return false;
        }
        return true;
    }

    /**
     * 配置跨域
     * @param response
     */
    private void crossDomainConfig(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication,Origin, X-Requested-With, Content-Type, " + "Accept, x-access-token");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires ", "-1");
    }

}
