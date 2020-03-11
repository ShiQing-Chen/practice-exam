package com.example.practiceexam.param;

/**
 * @author ShiQing_Chen
 * @since 2019/8/9 13:42
 */
public class SearchMessageParam {
    private String sort;
    private String order;
    private Integer offset;
    private Integer limit;
    /**
     * 消息状态 1未读 2已读
     */
    private Integer messageStatus;

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

    public Integer getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }
}
