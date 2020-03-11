package com.example.practiceexam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 消息
 * @author ShiQing_Chen  2020/3/11  17:39
 **/
@Entity
@Table(name = "message_info")
public class MessageInfo {
    // 系统消息 1
    public final static int TYPE_SYSTEM = 1;
    // 评论消息 2
    public final static int TYPE_COMMENT = 2;

    //消息状态 1未读
    public final static int STATUS_UNREAD = 1;
    //消息状态 2已读
    public final static int STATUS_READ = 2;

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
     * 消息类型 1系统消息  2评论通知
     */
    private Integer messageType;
    /**
     * 接受用户id
     */
    private Long acceptUserId;
    /**
     * 消息状态 1未读 2已读
     */
    private Integer messageStatus = STATUS_UNREAD;
    /**
     * 创建用户id
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

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

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Long getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(Long acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public Integer getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
