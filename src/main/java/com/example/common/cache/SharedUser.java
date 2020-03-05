package com.example.common.cache;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 共享用户用的，存放在redis/全局变量 中的用户对象<br/>
 */
public class SharedUser {

    private Long userId;
    private String nickName;
    private Integer userType;
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

    public Set<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(Set<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
}
