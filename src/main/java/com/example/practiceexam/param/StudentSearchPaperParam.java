package com.example.practiceexam.param;

import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:42
 */
public class StudentSearchPaperParam {
    private String sort;
    private String order;
    private Integer offset;
    private Integer limit;
    @Size(max = 35, message = "搜索内容长度不超过35个字符！")
    private String search;
    /**
     * 试卷类型 1考试 2测试 3练习
     */
    private Integer paperType;
    /**
     * 试卷状态 1草稿 2发布
     */
    private Integer paperStatus;
    /**
     * 班级ID
     */
    private Long classId;
    /**
     * 学生ID
     */
    private Long studentId;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
