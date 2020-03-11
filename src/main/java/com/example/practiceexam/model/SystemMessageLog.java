package com.example.practiceexam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/11  19:33
 **/
@Entity
@Table(name = "system_message_log")
public class SystemMessageLog {

    /**
     * 消息ID
     */
    @Id
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
