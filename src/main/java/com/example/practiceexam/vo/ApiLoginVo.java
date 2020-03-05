package com.example.practiceexam.vo;

/**
 * @author ShiQing_Chen  2020/3/5  19:21
 **/
public class ApiLoginVo {
    /**
     * 接口请求凭证
     */
    private String token;
    /**
     * 刷新token 的参数
     */
    private String refreshToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
