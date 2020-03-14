package com.example.practiceexam.dto;

/**
 * @author ShiQing_Chen  2020/3/12  10:24
 **/
public class GenerateQuesDto {
    /**
     * 试题ID
     */
    private Long questionId;
    /**
     * 试题编号
     */
    private String questionCode;
    /**
     * 试题类型 1选择题 2操作题
     */
    private Integer questionType;
    /**
     * 试题状态 1草稿 2待审核 3审核未通过 4审核通过
     */
    private Integer questionStatus;
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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(Integer questionStatus) {
        this.questionStatus = questionStatus;
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

}
