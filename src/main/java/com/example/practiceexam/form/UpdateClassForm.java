package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:38
 */
public class UpdateClassForm {
    /**
     * 班级id
     */
    @NotNull(message = "班级ID不能为空！")
    private Long classId;
    /**
     * 年级 例2019 2020
     */
    @NotEmpty(message = "年级不能为空！")
    @Size(max = 20, message = "年级长度不能超过20个字符！")
    private String grade;
    /**
     * 专业
     */
    @NotEmpty(message = "专业不能为空！")
    @Size(max = 50, message = "专业长度不能超过50个字符！")
    private String majorName;
    /**
     * 班级名称
     */
    @NotEmpty(message = "班级名称不能为空！")
    @Size(max = 50, message = "班级名称长度不能超过50个字符！")
    private String className;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
