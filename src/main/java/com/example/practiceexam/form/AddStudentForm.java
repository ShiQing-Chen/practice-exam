package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:38
 */
public class AddStudentForm {
    /**
     * 学号
     */
    @NotEmpty(message = "学号不能为空！")
    @Size(min = 8, max = 8, message = "学号长度为8位数，并且开头不能为0！")
    private String studentNumber;
    /**
     * 学生名称
     */
    @NotEmpty(message = "学生名称不能为空！")
    @Size(min = 2, max = 30, message = "学生名称长度为 2 ~ 30 个字符！")
    private String studentName;
    /**
     * 性别:1男，2女
     */
    private Integer gender;
    /**
     * 班级ID
     */
    private Long classId;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
