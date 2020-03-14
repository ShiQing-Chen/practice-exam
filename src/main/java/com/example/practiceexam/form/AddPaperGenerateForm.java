package com.example.practiceexam.form;

import com.example.practiceexam.dto.GenerateQuesDto;
import com.google.common.collect.Lists;

import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "试题不能为空！")
    private List<GenerateQuesDto> quesList = Lists.newArrayList();
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
    private BigDecimal subjectiveScore;


    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public List<GenerateQuesDto> getQuesList() {
        return quesList;
    }

    public void setQuesList(List<GenerateQuesDto> quesList) {
        this.quesList = quesList;
    }

    public BigDecimal getChoiceScore() {
        return choiceScore;
    }

    public void setChoiceScore(BigDecimal choiceScore) {
        this.choiceScore = choiceScore;
    }

    public BigDecimal getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(BigDecimal subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }
}
