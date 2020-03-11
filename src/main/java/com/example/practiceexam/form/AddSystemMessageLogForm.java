package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/11  19:45
 **/
public class AddSystemMessageLogForm {
    /**
     * 消息内容
     */
    @NotEmpty(message = "消息内容不能为空！")
    @Size(max = 500, message = "消息内容长度不能超过 1000 个字符！")
    private String messageContent;
    /**
     * 接受用户类型
     */
    @NotEmpty(message = "接受用户类型不能为空！")
    private List<Integer> acceptUserType;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public List<Integer> getAcceptUserType() {
        return acceptUserType;
    }

    public void setAcceptUserType(List<Integer> acceptUserType) {
        this.acceptUserType = acceptUserType;
    }
}
