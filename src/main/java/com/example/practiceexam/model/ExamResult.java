package com.example.practiceexam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 答题结果表
 * @author ShiQing_Chen  2020/3/15  13:20
 **/
@Entity
@Table(name = "exam_result")
public class ExamResult {
    public final static int STATUS_NOT_MARK = 1;
    public final static int STATUS_MARK = 2;
    /**
     * 结果ID
     */
    @Id
    private Long resultId;
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 学生ID
     */
    private Long studentId;
    /**
     * 试题ID
     */
    private Long questionId;
    /**
     * 学生答案
     */
    private String resultAnswer;
    /**
     * 批改状态 1未批改 2已批改
     */
    private Integer resultStatus;
    /**
     * 教师批改意见
     */
    private String resultOpinion;
    /**
     * 试题得分
     */
    private BigDecimal resultScore = BigDecimal.ZERO;
    /**
     * 创建用户id
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 批改用户id
     */
    private Long markUserId;
    /**
     * 批改时间
     */
    private Date markTime;

    public static int getStatusNotMark() {
        return STATUS_NOT_MARK;
    }

    public static int getStatusMark() {
        return STATUS_MARK;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getResultAnswer() {
        return resultAnswer;
    }

    public void setResultAnswer(String resultAnswer) {
        this.resultAnswer = resultAnswer;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultOpinion() {
        return resultOpinion;
    }

    public void setResultOpinion(String resultOpinion) {
        this.resultOpinion = resultOpinion;
    }

    public BigDecimal getResultScore() {
        return resultScore;
    }

    public void setResultScore(BigDecimal resultScore) {
        this.resultScore = resultScore;
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

    public Long getMarkUserId() {
        return markUserId;
    }

    public void setMarkUserId(Long markUserId) {
        this.markUserId = markUserId;
    }

    public Date getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Date markTime) {
        this.markTime = markTime;
    }
}
