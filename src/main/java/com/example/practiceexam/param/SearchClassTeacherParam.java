package com.example.practiceexam.param;

import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen  2020/3/7  17:52
 **/
public class SearchClassTeacherParam {
    private String sort;
    private String order;
    private Integer offset;
    private Integer limit;
    @Size(max = 20, message = "搜索内容长度不超过20个字符！")
    private String search;

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
}
