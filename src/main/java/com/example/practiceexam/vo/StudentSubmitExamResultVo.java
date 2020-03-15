package com.example.practiceexam.vo;

/**
 * @author ShiQing_Chen  2020/3/15  15:26
 **/
public class StudentSubmitExamResultVo {
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 学生ID
     */
    private Long studentId;
    /**
     * 试题ID
     */
    private Long questionId;
    /**
     * 学生答案
     */
    private String resultAnswer;
    /**
     * 试题类型 1选择题 2操作题
     */
    private Integer questionType;

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getResultAnswer() {
        return resultAnswer;
    }

    public void setResultAnswer(String resultAnswer) {
        this.resultAnswer = resultAnswer;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
}
