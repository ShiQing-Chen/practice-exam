package com.example.practiceexam.param;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:42
 */
public class SearchArticleParam {
    private String sort;
    private String order;
    private Integer offset;
    private Integer limit;
    @Size(max = 20, message = "搜索内容长度不超过20个字符！")
    private String search;
    private List<Integer> articleType;
    /**
     * 是否查询自己的
     */
    private Boolean searchMy = false;
    /**
     * 创建人ID
     */
    private Long createUserId;

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

    public List<Integer> getArticleType() {
        return articleType;
    }

    public void setArticleType(List<Integer> articleType) {
        this.articleType = articleType;
    }

    public Boolean getSearchMy() {
        return searchMy;
    }

    public void setSearchMy(Boolean searchMy) {
        this.searchMy = searchMy;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
