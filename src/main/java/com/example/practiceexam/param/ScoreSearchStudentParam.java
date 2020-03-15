package com.example.practiceexam.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:42
 */
public class ScoreSearchStudentParam {
    private String sort;
    private String order;
    private Integer offset;
    private Integer limit;
    @Size(max = 20, message = "搜索内容长度不超过20个字符！")
    private String search;
    private String grade;
    private String majorName;
    private Long classId;
    private List<Long> classIdList;
    @NotNull(message = "试卷ID不能为空！")
    private Long paperId;


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

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public List<Long> getClassIdList() {
        return classIdList;
    }

    public void setClassIdList(List<Long> classIdList) {
        this.classIdList = classIdList;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }
}
