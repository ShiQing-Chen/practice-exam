package com.example.practiceexam.argument;

import com.example.common.cache.SharedUser;
import com.example.common.exception.NeedLoginException;
import com.example.common.util.JwtTokenUtils;
import com.example.practiceexam.config.OnlineUserManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 支持将SharedUser 作为controller 参数直接使用
 * @author HanHongmin
 * @since 5.1.0
 */
public class SharedUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return SharedUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory) {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            throw new NeedLoginException("需要登录才能继续访问");
        }
        Long userId = JwtTokenUtils.getUserId(jwtSecretKey, token);
        if (userId == null) {
            throw new NeedLoginException("需要登录才能继续访问");
        }
        SharedUser sharedUser = OnlineUserManager.getUserClient(userId);
        if (sharedUser == null) {
            throw new NeedLoginException("需要登录才能继续访问");
        }
        return sharedUser;
    }
}
