package com.example.practiceexam.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen  2020/3/14  12:37
 **/
public class GenerateSearchQuesParam {
    private String sort;
    private String order;
    private Integer offset;
    private Integer limit;
    @Size(max = 35, message = "搜索内容长度不超过35个字符！")
    private String search;
    /**
     * 试卷ID
     */
    @NotNull(message = "试卷ID不能为空！")
    private Long paperId;
    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 试题来源 1题库 2当前用户
     */
    @NotNull(message = "试题来源不能为空！")
    private Integer questionSource;
    /**
     * 创建试题用户ID
     */
    private Long createUserId;
    /**
     * 试题状态 1草稿 2待审核 3审核未通过 4审核通过
     */
    private Integer questionStatus;
    /**
     * 试题类型 1选择题 2操作题
     */
    private Integer questionType;
    /**
     * 试题难易程度 1简单 2中等 3困难
     */
    private Integer questionDifficulty;

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

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getQuestionSource() {
        return questionSource;
    }

    public void setQuestionSource(Integer questionSource) {
        this.questionSource = questionSource;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(Integer questionStatus) {
        this.questionStatus = questionStatus;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(Integer questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }
}
