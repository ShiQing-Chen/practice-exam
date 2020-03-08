package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:38
 */
public class AddTeacherForm {
    /**
     * 教师工号
     */
    @NotEmpty(message = "工号不能为空！")
    @Size(min = 5, max = 5, message = "工号长度为5位数，并且开头不能为0！")
    private String teacherNumber;
    /**
     * 教师名称
     */
    @NotEmpty(message = "教师名称不能为空！")
    @Size(min = 2, max = 30, message = "教师名称长度为 2 ~ 30 个字符！")
    private String teacherName;
    /**
     * 性别:1男，2女
     */
    private Integer gender;

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
