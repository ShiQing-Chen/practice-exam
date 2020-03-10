package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen  2020/3/11  00:48
 **/
public class AddArticleForm {
    /**
     * 帖子标题
     */
    @NotEmpty(message = "标题不能为空！")
    @Size(max = 200, message = "标题长度不能超过200个字符！")
    private String articleTitle;
    /**
     * 帖子内容
     */
    @NotEmpty(message = "内容不能为空！")
    @Size(max = 1500, message = "内容长度不能超过1500个字符！")
    private String articleContent;
    /**
     * 帖子类型
     * 1想知道  2技术分享  3生活休闲
     */
    @NotNull(message = "帖子类型不能为空！")
    private Integer articleType;

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
}
