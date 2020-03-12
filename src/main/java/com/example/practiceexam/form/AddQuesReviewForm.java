package com.example.practiceexam.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 试题审核
 * @author ShiQing_Chen  2020/3/12  23:08
 **/
public class AddQuesReviewForm {
    /**
     * 试题ID
     */
    @NotNull(message = "试题ID不能为空！")
    private Long questionId;
    /**
     * 审核结果 1通过 2不通过
     */
    @NotNull(message = "审核结果不能为空！")
    private Integer reviewResult;
    /**
     * 审核意见
     */
    @Size(max = 300, message = "审核意见长度不能超过 300 个字符！")
    private String reviewOpinion;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(Integer reviewResult) {
        this.reviewResult = reviewResult;
    }

    public String getReviewOpinion() {
        return reviewOpinion;
    }

    public void setReviewOpinion(String reviewOpinion) {
        this.reviewOpinion = reviewOpinion;
    }
}
