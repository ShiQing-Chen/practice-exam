package com.example.practiceexam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 试题审核记录表
 * @author ShiQing_Chen  2020/3/12  23:01
 **/
@Entity
@Table(name = "question_review_log")
public class QuestionReviewLog {
    public final static int STATUS_PASS = 2;
    public final static int STATUS_NOT_PASS = 1;

    /**
     * 审核记录ID
     */
    @Id
    private Long reviewId;
    /**
     * 试题ID
     */
    private Long questionId;
    /**
     * 审核结果 1通过 2不通过
     */
    private Integer reviewResult;
    /**
     * 审核意见
     */
    private String reviewOpinion;
    /**
     * 创建用户id
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    private Date createTime;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(Integer reviewResult) {
        this.reviewResult = reviewResult;
    }

    public String getReviewOpinion() {
        return reviewOpinion;
    }

    public void setReviewOpinion(String reviewOpinion) {
        this.reviewOpinion = reviewOpinion;
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
