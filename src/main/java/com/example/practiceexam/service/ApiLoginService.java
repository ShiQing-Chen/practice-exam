package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录登出、获取登录用户基本信息
 * @author ShiQing_Chen  2020/3/5  18:17
 **/
public interface ApiLoginService {
    /**
     * 登录
     * @param request
     * @param loginText 登录账号
     * @param password  登录密码
     * @return
     */
    MessageVo login(HttpServletRequest request, String loginText, String password);

    /**
     * 刷新token
     * @param refreshToken
     * @return
     */
    MessageVo refreshToken(String refreshToken);

    /**
     * 退出登录
     * @param sharedUser 共享参数
     * @return 操作结果
     */
    MessageVo logout(SharedUser sharedUser);

    /**
     * 用户信息详情
     * @param sharedUser 登录用户
     * @return
     */
    MessageVo getUserInfo(SharedUser sharedUser);
}
