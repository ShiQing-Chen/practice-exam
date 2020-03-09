package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/13 21:34
 */
public class PasswordForm {
    @Size(min = 6, max = 18, message = "密码长度为6~18个字符！")
    @NotEmpty(message = "密码不能为空！")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
