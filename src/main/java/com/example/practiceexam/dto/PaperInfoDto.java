package com.example.practiceexam.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/14  02:32
 **/
public class PaperInfoDto {
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 试卷名称
     */
    private String paperName;
    /**
     * 考试/检测时长 (分钟)
     */
    private Integer doTime;
    /**
     * 试卷类型 1考试 2测试 3练习
     */
    private Integer paperType;
    /**
     * 试卷状态 1草稿 2发布
     */
    private Integer paperStatus;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 学生数量
     */
    private Integer studentTotal;
    /**
     * 发布用户id
     */
    private Long publishUserId;
    /**
     * 发布用户姓名
     */
    private String publishUserName;
    /**
     * 发布时间
     */
    private Date publishTime;
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
    /**
     * 答题结果数量
     */
    private Integer resultNumber;
    /**
     * 答题结果得分
     */
    private BigDecimal totalScore;
    /**
     * 完成学生数量
     */
    private Integer finishStudentNumber;

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getDoTime() {
        return doTime;
    }

    public void setDoTime(Integer doTime) {
        this.doTime = doTime;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }

    public Integer getPaperStatus() {
        return paperStatus;
    }

    public void setPaperStatus(Integer paperStatus) {
        this.paperStatus = paperStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStudentTotal() {
        return studentTotal;
    }

    public void setStudentTotal(Integer studentTotal) {
        this.studentTotal = studentTotal;
    }

    public Long getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Long publishUserId) {
        this.publishUserId = publishUserId;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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

    public Integer getResultNumber() {
        return resultNumber;
    }

    public void setResultNumber(Integer resultNumber) {
        this.resultNumber = resultNumber;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getFinishStudentNumber() {
        return finishStudentNumber;
    }

    public void setFinishStudentNumber(Integer finishStudentNumber) {
        this.finishStudentNumber = finishStudentNumber;
    }
}
