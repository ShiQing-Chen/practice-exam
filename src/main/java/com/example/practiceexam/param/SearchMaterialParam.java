package com.example.practiceexam.param;

import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:42
 */
public class SearchMaterialParam {
    private String sort;
    private String order;
    private Integer offset;
    private Integer limit;
    @Size(max = 20, message = "搜索内容长度不超过20个字符！")
    private String search;
    private Integer materialStatus;

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

    public Integer getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialStatus(Integer materialStatus) {
        this.materialStatus = materialStatus;
    }
}
