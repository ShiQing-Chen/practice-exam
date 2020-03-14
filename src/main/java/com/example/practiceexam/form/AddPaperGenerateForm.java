package com.example.practiceexam.form;

import com.google.common.collect.Lists;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/14  12:23
 **/
public class AddPaperGenerateForm {
    /**
     * 试卷ID
     */
    @NotNull(message = "试卷ID不能为空！")
    private Long paperId;
    /**
     * 试题ID
     */
    private List<Long> quesIdList = Lists.newArrayList();
    /**
     * 试题分数
     * 选择题
     */
    @NotNull(message = "选择题分数不能为空！")
    private BigDecimal choiceScore;
    /**
     * 试题分数
     * 非选择题
     */
    @NotNull(message = "非选择题分数不能为空！")
    private BigDecimal subjectivScore;


    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public List<Long> getQuesIdList() {
        return quesIdList;
    }

    public void setQuesIdList(List<Long> quesIdList) {
        this.quesIdList = quesIdList;
    }

    public BigDecimal getChoiceScore() {
        return choiceScore;
    }

    public void setChoiceScore(BigDecimal choiceScore) {
        this.choiceScore = choiceScore;
    }

    public BigDecimal getSubjectivScore() {
        return subjectivScore;
    }

    public void setSubjectivScore(BigDecimal subjectivScore) {
        this.subjectivScore = subjectivScore;
    }
}
