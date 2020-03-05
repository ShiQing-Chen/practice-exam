package com.example.practiceexam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 交流驿站
 * 帖子信息表
 * @author ShiQing_Chen  2020/3/5  00:48
 **/
@Entity
@Table(name = "article_info")
public class ArticleInfo {
    /**
     * 帖子id
     */
    @Id
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
     */
    private Integer articleType;
    /**
     * 创建用户id
     */
    private Long createUserId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
