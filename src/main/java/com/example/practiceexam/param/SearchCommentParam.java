package com.example.practiceexam.param;


import javax.validation.constraints.NotNull;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:42
 */
public class SearchCommentParam {
    private String sort;
    private String order;
    private Integer offset;
    private Integer limit;
    /**
     * 帖子id
     */
    @NotNull(message = "帖子id不能为空！")
    private Long articleId;

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

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
