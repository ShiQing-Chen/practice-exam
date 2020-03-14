package com.example.practiceexam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 试卷信息表
 * @author ShiQing_Chen  2020/3/14  01:23
 **/
@Entity
@Table(name = "paper_info")
public class PaperInfo {
    /**
     * 草稿
     */
    public final static int STATUS_DRAFT = 1;
    /**
     * 发布
     */
    public final static int STATUS_PUBLIC = 2;
    /**
     * 试卷ID
     */
    @Id
    private Long paperId;
    /**
     * 课程ID
     */
    private Long courseId;
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
     * 发布用户id
     */
    private Long publishUserId;
    /**
     * 发布时间
     */
    private Date publishTime;
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

    public Long getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Long publishUserId) {
        this.publishUserId = publishUserId;
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
