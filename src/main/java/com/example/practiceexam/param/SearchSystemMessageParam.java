package com.example.practiceexam.param;

import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:42
 */
public class SearchSystemMessageParam {
    private String sort;
    private String order;
    private Integer offset;
    private Integer limit;
    @Size(max = 30, message = "搜索内容长度不超过30个字符！")
    private String search;
    /**
     * 接受用户类型
     */
    private Integer acceptUserType;

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

    public Integer getAcceptUserType() {
        return acceptUserType;
    }

    public void setAcceptUserType(Integer acceptUserType) {
        this.acceptUserType = acceptUserType;
    }
}
