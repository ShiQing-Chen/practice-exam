package com.example.practiceexam.form;

import com.example.practiceexam.model.UserInfo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:38
 */
public class AddUserForm {
    /**
     * 登录账户，唯一
     */
    @NotEmpty(message = "登录账户不能为空！")
    @Size(min = 4, max = 30, message = "登录账户长度为4~30个字符！")
    private String loginName;
    /**
     * 明文密码
     */
    @NotEmpty(message = "密码不能为空！")
    @Size(min = 6, max = 18, message = "密码长度为6~18个字符！")
    private String loginPass;
    /**
     * 昵称
     */
    @Size(min = 2, max = 30, message = "昵称长度为2~30个字符！")
    private String nickName;
    /**
     * 头像地址
     */
    @Size(max = 200, message = "头像地址长度不能超过200个字符！")
    private String avatar = UserInfo.AVATAR_DEFAULT;
    /**
     * 用户类型:1管理员，2教师，3学生
     */
    private Integer userType;
    /**
     * 性别:1男，2女
     */
    private Integer gender;
    /**
     * 用户手机号码，唯一
     */
    private String mobile;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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
