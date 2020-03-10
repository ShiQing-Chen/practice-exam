package com.example.practiceexam.dto;

import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/11  01:04
 **/
public class ArticleDto {
    /**
     * 帖子id
     */
    private Long articleId;
    /**
     * 帖子标题
     */
    private String articleTitle;
    /**
     * 帖子类型
     * 1想知道  2技术分享  3生活休闲
     */
    private Integer articleType;
    /**
     * 创建用户id
     */
    private Long createUserId;
    /**
     * 创建用户
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
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
