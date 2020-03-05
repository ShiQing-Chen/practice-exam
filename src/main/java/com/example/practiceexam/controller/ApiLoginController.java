package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.param.RefreshToken;
import com.example.practiceexam.service.ApiLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录登出
 * @author ShiQing_Chen  2020/3/5  18:15
 **/
@Controller
public class ApiLoginController {
    private final ApiLoginService apiLoginService;

    @Autowired
    public ApiLoginController(ApiLoginService apiLoginService) {
        this.apiLoginService = apiLoginService;
    }


    /**
     * 前后端分离，使用jwt 方式登录
     */
    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public MessageVo login(HttpServletRequest request, String loginText, String loginPass) {
        if (StringUtils.isBlank(loginText) || StringUtils.isBlank(loginPass)) {
            return new MessageVo(false, "缺少参数");
        }
        return apiLoginService.login(request, loginText, loginPass);
    }

    /**
     * 提供给app 等前后分离的前端登录功能，使用jwt 方式
     */
    @RequestMapping(value = "/api/user/refreshToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public MessageVo refreshToken(@RequestBody RefreshToken param) {
        return apiLoginService.refreshToken(param.getRefreshToken());
    }

    /**
     * 登出
     */
    @RequestMapping(value = "/api/user/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public MessageVo logout(SharedUser sharedUser) {
        return apiLoginService.logout(sharedUser);
    }

    /**
     * 用户信息
     */
    @RequestMapping(value = "/api/user/getUserInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public MessageVo getUserInfo(SharedUser sharedUser) {
        return apiLoginService.getUserInfo(sharedUser);
    }
}
