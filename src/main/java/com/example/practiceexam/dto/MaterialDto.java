package com.example.practiceexam.dto;

import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/8  19:35
 **/
public class MaterialDto {
    /**
     * 资料id
     */
    private Long materialId;
    /**
     * 主标题
     */
    private String mainTitle;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 资料状态 1草稿 2发布
     */
    private Integer materialStatus;
    /**
     * 发布用户id
     */
    private Long publishUserId;
    /**
     * 发布用户姓名
     */
    private String publishUserName;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 创建用户id
     */
    private Long createUserId;
    /**
     * 创建用户姓名
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialStatus(Integer materialStatus) {
        this.materialStatus = materialStatus;
    }

    public Long getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Long publishUserId) {
        this.publishUserId = publishUserId;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
