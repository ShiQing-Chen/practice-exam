package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddQuesReviewForm;

/**
 * @author ShiQing_Chen  2020/3/12  23:07
 **/
public interface QuestionReviewLogService {
    /**
     * 添加
     * @param reviewForm
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddQuesReviewForm reviewForm);
}
