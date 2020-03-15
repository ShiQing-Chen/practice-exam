package com.example.practiceexam.form;

import com.example.practiceexam.vo.StudentSubmitExamResultVo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/15  15:25
 **/
public class AddExamResultForm {

    /**
     * 试卷ID
     */
    @NotNull(message = "试卷ID不能为空！")
    private Long paperId;

    @NotEmpty(message = "提交结果不能为空！")
    private List<StudentSubmitExamResultVo> resultList;

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public List<StudentSubmitExamResultVo> getResultList() {
        return resultList;
    }

    public void setResultList(List<StudentSubmitExamResultVo> resultList) {
        this.resultList = resultList;
    }
}
