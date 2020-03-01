package com.example.common.cache;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 共享用户用的，存放在redis/全局变量 中的用户对象<br/>
 */
public class SharedUser {

    private Long userId;
    private String nickname;
    private Integer userType;
    private Long orgId;
    private Long provId;
    private Set<String> roleCodes = Sets.newHashSet();
    private Set<String> funcCodes = Sets.newHashSet();
    private String loginFrom;

    /**
     * 对象缓存key 的获取
     * @return redis 的key
     */
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getProvId() {
        return provId;
    }

    public void setProvId(Long provId) {
        this.provId = provId;
    }

    public Set<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(Set<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public Set<String> getFuncCodes() {
        return funcCodes;
    }

    public void setFuncCodes(Set<String> funcCodes) {
        this.funcCodes = funcCodes;
    }

    public String getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(String loginFrom) {
        this.loginFrom = loginFrom;
    }
}
