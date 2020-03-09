package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddUserForm;
import com.example.practiceexam.form.UpdateMyUserForm;
import com.example.practiceexam.form.UpdateUserForm;
import com.example.practiceexam.param.SearchUserParam;

/**
 * @author ShiQing_Chen  2020/3/5  17:59
 **/
public interface UserInfoService {
    /**
     * 添加用户
     * @param addUserForm
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddUserForm addUserForm);

    /**
     * 修改用户信息
     * @param userForm
     * @return
     */
    MessageVo update(SharedUser sharedUser, UpdateUserForm userForm);

    /**
     * 重置用户密码
     * 如果有手机号则重置为手机号，没有则重置为111111
     * @param userId
     * @return
     */
    MessageVo resetPassword(Long userId);

    /**
     * 根据id获取用户信息
     * 携带系统角色id
     * 组织角色id
     * @param userId
     * @return
     */
    MessageVo getById(Long userId);

    /**
     * 根据id删除用户
     * @param userId
     * @return
     */
    MessageVo delById(Long userId);

    /**
     * 根据id禁用用户
     * @param userId
     * @return
     */
    MessageVo disableById(Long userId);

    /**
     * 根据id启用用户
     * @param userId
     * @return
     */
    MessageVo enableById(Long userId);

    /**
     * 获取用户详细信息
     * @param userId
     * @return
     */
    MessageVo getInfoById(Long userId);

    /**
     * 添加
     * 校验手机号
     * @param mobile
     * @return
     */
    MessageVo checkMobile(String mobile);

    /**
     * 修改
     * 校验手机号
     * @param mobile
     * @return
     */
    MessageVo checkMobileById(Long userId, String mobile);


    /**
     * 添加
     * 校验登录账号
     * @param loginName
     * @return
     */
    MessageVo checkLoginName(String loginName);

    /**
     * 修改
     * 校验登录账号
     * @param userId
     * @param loginName
     * @return
     */
    MessageVo checkLoginNameById(Long userId, String loginName);

    /**
     * 更换头像
     * @param userId
     * @param avatar
     * @return
     */
    MessageVo changeAvatar(Long userId, String avatar);

    /**
     * 管理员
     * 分页查询
     * @param userParam
     * @return
     */
    MessageVo adminGetListByPage(SearchUserParam userParam);

    /**
     * 修改个人信息
     * @param userForm
     * @return
     */
    MessageVo updateMyInfo(UpdateMyUserForm userForm);

    /**
     * 修改个人密码
     * 校验旧密码
     * @param sharedUser
     * @param password
     * @return
     */
    MessageVo checkOldPassword(SharedUser sharedUser, String password);

    /**
     * 修改个人密码
     * @param sharedUser
     * @param password
     * @return
     */
    MessageVo updatePassword(SharedUser sharedUser, String password);
}
