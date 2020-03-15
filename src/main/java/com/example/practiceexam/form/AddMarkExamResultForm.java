package com.example.practiceexam.form;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ShiQing_Chen  2020/3/15  23:38
 **/
public class AddMarkExamResultForm {
    /**
     * 结果ID
     */
    @NotNull(message = "结果ID不能为空！")
    private Long resultId;
    /**
     * 教师批改意见
     */
    private String resultOpinion;
    /**
     * 试题得分
     */
    @NotNull(message = "试题得分不能为空！")
    private BigDecimal resultScore = BigDecimal.ZERO;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getResultOpinion() {
        return resultOpinion;
    }

    public void setResultOpinion(String resultOpinion) {
        this.resultOpinion = resultOpinion;
    }

    public BigDecimal getResultScore() {
        return resultScore;
    }

    public void setResultScore(BigDecimal resultScore) {
        this.resultScore = resultScore;
    }
}
