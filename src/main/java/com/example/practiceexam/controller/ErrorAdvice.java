package com.example.practiceexam.controller;

import com.example.common.exception.NeedLoginException;
import com.example.common.util.ServerInfoUtils;
import com.example.common.vo.MessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HanHongmin
 * @since 2019-10-08
 */
@ControllerAdvice
public class ErrorAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorAdvice.class);

    @Value("${spring.application.name}")
    private String applicationName;

    @ExceptionHandler(RuntimeException.class)
    public Object defaultError(HttpServletRequest request, Exception e) {
        String errorCode = this.getErrorCode();
        LOGGER.error("====错误:[{}]====[{}]", errorCode, request.getRequestURL(), e);

        MessageVo result = new MessageVo(false, "服务器走神儿了", errorCode);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
    }

    @ExceptionHandler(MultipartException.class)
    public Object multipartError() {
        MessageVo result = new MessageVo(false, "未能获取到上传的文件内容");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
    }

    private String getErrorCode() {
        //时间戳,只适用于查找具体报错日志方便
        long currentTimeMillis = System.currentTimeMillis();
        String serverIp = ServerInfoUtils.getServerIp();
        String ipLast = serverIp.substring(serverIp.lastIndexOf('.') + 1);
        return ipLast + "-" + currentTimeMillis;
    }


    @ExceptionHandler(NeedLoginException.class)
    public Object needLogin() {
        MessageVo result = new MessageVo(false, "需要登录才能继续访问");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleError404() {
        MessageVo result = new MessageVo(false, "您要访问的资源没有找到");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object jsonError() {
        MessageVo result = new MessageVo(false, "参数格式不正确");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
    }
}
