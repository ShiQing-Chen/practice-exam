package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddUserForm;
import com.example.practiceexam.form.UpdateUserForm;
import com.example.practiceexam.param.SearchUserParam;
import com.example.practiceexam.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ShiQing_Chen  2020/3/5  18:06
 **/
@Controller
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * 添加用户
     * @param sharedUser
     * @param userForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddUserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return userInfoService.add(sharedUser, userForm);
    }

    /**
     * 修改用户信息
     * @param userForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdateUserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return userInfoService.update(sharedUser, userForm);
    }

    /**
     * 重置用户密码
     * 重置为111111
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo resetPassword(Long userId) {
        if (userId == null) {
            return MessageVo.fail("缺少用户ID参数！");
        }
        return userInfoService.resetPassword(userId);
    }

    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long userId) {
        if (userId == null) {
            return MessageVo.fail("缺少用户ID参数！");
        }
        return userInfoService.getById(userId);
    }

    /**
     * 获取用户详细信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/getInfoById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getInfoById(Long userId) {
        if (userId == null) {
            return MessageVo.fail("缺少用户ID参数！");
        }
        return userInfoService.getInfoById(userId);
    }

    /**
     * 根据id删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long userId) {
        if (userId == null) {
            return MessageVo.fail("缺少用户ID参数！");
        }
        return userInfoService.delById(userId);
    }

    /**
     * 根据id禁用用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/disableById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo disableById(Long userId) {
        if (userId == null) {
            return MessageVo.fail("缺少用户ID参数！");
        }
        return userInfoService.disableById(userId);
    }

    /**
     * 根据id启用用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/enableById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo enableById(Long userId) {
        if (userId == null) {
            return MessageVo.fail("缺少用户ID参数！");
        }
        return userInfoService.enableById(userId);
    }


    /**
     * 添加
     * 校验手机号
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/user/checkMobile", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkMobile(String mobile) {
        return userInfoService.checkMobile(mobile);
    }

    /**
     * 修改
     * 校验手机号
     * @param userId
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/user/checkMobileById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkMobileById(Long userId, String mobile) {
        return userInfoService.checkMobileById(userId, mobile);
    }


    /**
     * 添加
     * 校验登录账号
     * @param loginName
     * @return
     */
    @RequestMapping(value = "/user/checkLoginName", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkLoginName(String loginName) {
        return userInfoService.checkLoginName(loginName);
    }

    /**
     * 修改
     * 校验登录账号
     * @param userId
     * @param loginName
     * @return
     */
    @RequestMapping(value = "/user/checkLoginNameById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkLoginNameById(Long userId, String loginName) {
        return userInfoService.checkLoginNameById(userId, loginName);
    }

    /**
     * 更换头像
     * @param userId
     * @param avatar
     * @return
     */
    @RequestMapping(value = "/user/changeAvatar", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo changeAvatar(Long userId, String avatar) {
        return userInfoService.changeAvatar(userId, avatar);
    }

    /**
     * 管理员
     * 分页查询
     * @param userParam
     * @return
     */
    @RequestMapping(value = "/admin/user/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo adminGetListByPage(@RequestBody @Valid SearchUserParam userParam, BindingResult bindingResult) {
        if (userParam == null) {
            return MessageVo.fail("获取用户数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return userInfoService.adminGetListByPage(userParam);
    }


}
