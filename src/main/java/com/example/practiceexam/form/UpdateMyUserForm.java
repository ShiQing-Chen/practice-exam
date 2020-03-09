package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:38
 */
public class UpdateMyUserForm {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空！")
    private Long userId;
    /**
     * 登录账户，唯一
     */
    @NotEmpty(message = "登录账户不能为空！")
    @Size(min = 4, max = 30, message = "登录账户长度为4~30个字符！")
    private String loginName;
    /**
     * 昵称
     */
    @Size(min = 2, max = 30, message = "昵称长度为2~30个字符！")
    private String nickName;
    /**
     * 真实姓名
     */
    @Size(min = 2, max = 30, message = "真实姓名长度为2~30个字符！")
    private String name;
    /**
     * 性别:1男，2女
     */
    private Integer gender;
    /**
     * 用户手机号码，唯一
     */
    private String mobile;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
