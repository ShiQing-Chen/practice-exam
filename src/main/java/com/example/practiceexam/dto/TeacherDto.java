package com.example.practiceexam.dto;

import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/7  22:34
 **/
public class TeacherDto {
    /**
     * 教师id
     */
    private Long teacherId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 教师工号
     */
    private String teacherNumber;
    /**
     * 教师名称
     */
    private String teacherName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 用户类型:1管理员，2教师，3学生
     */
    private Integer userType;
    /**
     * 性别:1男，2女
     */
    private Integer gender;
    /**
     * 用户手机号码，唯一
     */
    private String mobile;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
