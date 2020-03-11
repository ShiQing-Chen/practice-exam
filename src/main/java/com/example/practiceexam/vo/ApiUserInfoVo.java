package com.example.practiceexam.vo;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author ShiQing_Chen  2020/3/5  19:52
 **/
public class ApiUserInfoVo {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 未读消息数量
     */
    private Integer messageNumber = 0;
    /**
     * 用户角色编码
     */
    private Set<String> roleCodes = Sets.newHashSet();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(Integer messageNumber) {
        this.messageNumber = messageNumber;
    }

    public Set<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(Set<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
}
