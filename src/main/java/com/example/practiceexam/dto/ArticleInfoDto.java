package com.example.practiceexam.dto;

import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/11  15:44
 **/
public class ArticleInfoDto {
    /**
     * 帖子id
     */
    private Long articleId;
    /**
     * 帖子标题
     */
    private String articleTitle;
    /**
     * 帖子内容
     */
    private String articleContent;
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
     * 创建用户姓名
     */
    private String createUserName;
    /**
     * 创建用户头像
     */
    private String userAvatar;
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

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
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

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
