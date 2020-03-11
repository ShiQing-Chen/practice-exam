package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen  2020/3/11  16:20
 **/
public class AddCommentForm {
    /**
     * 帖子id
     */
    @NotNull(message = "帖子id不能为空！")
    private Long articleId;
    /**
     * 评论内容
     */
    @NotEmpty(message = "评论内容不能为空！")
    @Size(min = 2, max = 200, message = "评论内容长度不能超过 1000 个字符！")
    private String commentContent;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
