package com.example.practiceexam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 组卷关系表
 * @author ShiQing_Chen  2020/3/14  12:18
 **/
@Entity
@Table(name = "paper_generate")
public class PaperGenerate {
    /**
     * 组卷关系ID
     */
    @Id
    private Long generateId;
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 试题ID
     */
    private Long questionId;
    /**
     * 试题分数
     */
    private BigDecimal questionScore;
    /**
     * 试题序号
     */
    private Integer orderNumber;
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

    public Long getGenerateId() {
        return generateId;
    }

    public void setGenerateId(Long generateId) {
        this.generateId = generateId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public BigDecimal getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(BigDecimal questionScore) {
        this.questionScore = questionScore;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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
