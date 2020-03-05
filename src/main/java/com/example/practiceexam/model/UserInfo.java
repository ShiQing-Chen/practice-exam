package com.example.practiceexam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 基础用户表
 * @author ShiQing_Chen  2020/3/4  22:41
 **/
@Entity
@Table(name = "user_info")
public class UserInfo {
    /**
     * 用户类型 1管理员
     */
    public static final int TYPE_ADMIN = 1;
    /**
     * 用户类型 2教师
     */
    public static final int TYPE_TEACHER = 2;
    /**
     * 用户类型 3学生
     */
    public static final int TYPE_STUDENT = 3;

    /**
     * 性别 1男
     */
    public static final int GENDER_MALE = 1;
    /**
     * 性别 2女
     */
    public static final int GENDER_FEMALE = 2;

    /**
     * 用户状态 0禁用
     */
    public static final int STATUS_NOT_ACTIVE = 0;
    /**
     * 用户状态 1正常
     */
    public static final int STATUS_ACTIVED = 1;

    public static final String AVATAR_DEFAULT = "https://ecourse.pek3a.qingstor.com/header/default_header.png";

    /**
     * 用户id
     */
    @Id
    private Long userId;
    /**
     * 登录账户，唯一
     */
    private String loginName;
    /**
     * 加密密码
     */
    private String loginPass;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像地址
     */
    private String avatar = AVATAR_DEFAULT;
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
    /**
     * 状态0:未启用;1:已启用
     */
    private Integer userStatus = STATUS_ACTIVED;
    /**
     * refreshToken 专有名词, 刷新token 用，唯一
     */
    private String refreshToken;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

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

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
