package com.example.practiceexam.dto;

/**
 * @author ShiQing_Chen  2020/3/7  17:50
 **/
public class ClassTeacherDto {
    /**
     * 教师id
     */
    private Long teacherId;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 教师工号
     */
    private String teacherNumber;
    /**
     * 教师名称
     */
    private String teacherName;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

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
}
