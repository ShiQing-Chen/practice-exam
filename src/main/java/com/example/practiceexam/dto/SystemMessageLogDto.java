package com.example.practiceexam.dto;

import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/11  19:37
 **/
public class SystemMessageLogDto {
    /**
     * 消息ID
     */
    private Long messageId;
    /**
     * 消息内容
     */
    private String messageContent;
    /**
     * 接受用户类型
     */
    private Integer acceptUserType;
    /**
     * 创建用户id
     */
    private Long createUserId;
    /**
     * 创建用户姓名
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getAcceptUserType() {
        return acceptUserType;
    }

    public void setAcceptUserType(Integer acceptUserType) {
        this.acceptUserType = acceptUserType;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
