package com.example.practiceexam.vo;


/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:38
 */
public class AddStudentVo {
    /**
     * 学号
     */
    private String studentNumber;
    /**
     * 学生名称
     */
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
