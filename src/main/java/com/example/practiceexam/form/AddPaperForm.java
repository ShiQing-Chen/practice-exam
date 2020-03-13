package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/14  01:30
 **/
public class AddPaperForm {
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
     * 班级列表
     */
    private List<Long> classList;

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

    public List<Long> getClassList() {
        return classList;
    }

    public void setClassList(List<Long> classList) {
        this.classList = classList;
    }
}
