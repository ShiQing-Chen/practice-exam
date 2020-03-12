package com.example.practiceexam.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ShiQing_Chen  2020/3/11  00:48
 **/
public class AddQuesForm {
    /**
     * 课程ID
     */
    @NotNull(message = "课程ID不能为空！")
    private Long courseId;
    /**
     * 试题类型 1选择题 2操作题
     */
    @NotNull(message = "试题类型不能为空！")
    private Integer questionType;
    /**
     * 试题状态 1草稿 2待审核 3审核未通过 4审核通过
     */
    @NotNull(message = "试题状态不能为空！")
    private Integer questionStatus;
    /**
     * 试题难易程度 1简单 2中等 3困难
     */
    @NotNull(message = "试题难易程度不能为空！")
    private Integer questionDifficulty;
    /**
     * 题干
     */
    @NotEmpty(message = "题干不能为空！")
    @Size(max = 1000, message = "题干长度不能超过 1000 个字符！")
    private String questionTitle;
    /**
     * 答案
     */
    @NotEmpty(message = "答案不能为空！")
    @Size(max = 800, message = "答案长度不能超过 800 个字符！")
    private String questionAnswer;
    /**
     * 解析
     */
    @Size(max = 800, message = "解析长度不能超过 800 个字符！")
    private String questionAnalyze;
    /**
     * 选项A
     */
    @Size(max = 800, message = "选项A长度不能超过 800 个字符！")
    private String questionChoiceA;
    /**
     * 选项B
     */
    @Size(max = 800, message = "选项B长度不能超过 800 个字符！")
    private String questionChoiceB;
    /**
     * 选项C
     */
    @Size(max = 800, message = "选项C长度不能超过 800 个字符！")
    private String questionChoiceC;
    /**
     * 选项D
     */
    @Size(max = 800, message = "选项D长度不能超过 800 个字符！")
    private String questionChoiceD;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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
