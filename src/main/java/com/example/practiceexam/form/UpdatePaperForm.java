package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/14  01:31
 **/
public class UpdatePaperForm {
    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 试卷ID
     */
    @NotNull(message = "试卷ID不能为空！")
    private Long paperId;
    /**
     * 试卷名称
     */
    @NotEmpty(message = "试卷名称不能为空！")
    @Size(max = 150, message = "试卷名称长度不能超过 150 个字符！")
    private String paperName;
    /**
     * 考试/检测时长 (分钟)
     */
    private Integer doTime;
    /**
     * 试卷类型 1考试 2测试 3练习
     */
    @NotNull(message = "试卷类型不能为空！")
    private Integer paperType;
    /**
     * 试卷状态 1草稿 2发布
     */
    @NotNull(message = "试卷状态不能为空！")
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
     * 班级列表
     */
    private List<Long> classList;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
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

    public List<Long> getClassList() {
        return classList;
    }

    public void setClassList(List<Long> classList) {
        this.classList = classList;
    }
}
