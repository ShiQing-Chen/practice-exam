package com.example.practiceexam.dto;

import java.math.BigDecimal;

/**
 * @author ShiQing_Chen  2020/3/12  01:53
 **/
public class QuesInfoDto {
    /**
     * 试题ID
     */
    private Long questionId;
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 试题类型 1选择题 2操作题
     */
    private Integer questionType;
    /**
     * 试题难易程度 1简单 2中等 3困难
     */
    private Integer questionDifficulty;
    /**
     * 题干
     */
    private String questionTitle;
    /**
     * 答案
     */
    private String questionAnswer;
    /**
     * 解析
     */
    private String questionAnalyze;
    /**
     * 选项A
     */
    private String questionChoiceA;
    /**
     * 选项B
     */
    private String questionChoiceB;
    /**
     * 选项C
     */
    private String questionChoiceC;
    /**
     * 选项D
     */
    private String questionChoiceD;
    /**
     * 试题分数
     */
    private BigDecimal questionScore = BigDecimal.ZERO;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(Integer questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionAnalyze() {
        return questionAnalyze;
    }

    public void setQuestionAnalyze(String questionAnalyze) {
        this.questionAnalyze = questionAnalyze;
    }

    public String getQuestionChoiceA() {
        return questionChoiceA;
    }

    public void setQuestionChoiceA(String questionChoiceA) {
        this.questionChoiceA = questionChoiceA;
    }

    public String getQuestionChoiceB() {
        return questionChoiceB;
    }

    public void setQuestionChoiceB(String questionChoiceB) {
        this.questionChoiceB = questionChoiceB;
    }

    public String getQuestionChoiceC() {
        return questionChoiceC;
    }

    public void setQuestionChoiceC(String questionChoiceC) {
        this.questionChoiceC = questionChoiceC;
    }

    public String getQuestionChoiceD() {
        return questionChoiceD;
    }

    public void setQuestionChoiceD(String questionChoiceD) {
        this.questionChoiceD = questionChoiceD;
    }

    public BigDecimal getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(BigDecimal questionScore) {
        this.questionScore = questionScore;
    }
}
