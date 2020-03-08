package com.example.practiceexam.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:38
 */
public class AddCourseForm {
    /**
     * 课程名称
     */
    @NotEmpty(message = "课程名称不能为空！")
    @Size(min = 2, max = 100, message = "课程名称长度为 2 ~ 100 个字符！")
    private String courseName;
    /**
     * 封面地址
     */
    @Size(max = 200, message = "封面地址长度不能超过 200 个字符！")
    private String cover;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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
}
