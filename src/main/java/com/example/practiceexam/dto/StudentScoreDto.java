package com.example.practiceexam.dto;

import java.math.BigDecimal;

/**
 * @author ShiQing_Chen  2020/3/7  22:34
 **/
public class StudentScoreDto {
    /**
     * 学生id
     */
    private Long studentId;
    /**
     * 学号
     */
    private String studentNumber;
    /**
     * 学生名称
     */
    private String studentName;
    /**
     * 班级ID
     */
    private Long classId;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 年级 例2019 2020
     */
    private String grade;
    /**
     * 专业
     */
    private String majorName;
    /**
     * 分数
     */
    private BigDecimal score;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

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

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
