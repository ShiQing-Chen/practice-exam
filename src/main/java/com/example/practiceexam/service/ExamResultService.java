package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddExamResultForm;

/**
 * @author ShiQing_Chen  2020/3/15  15:22
 **/
public interface ExamResultService {
    /**
     * 添加
     * @param form
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddExamResultForm form);

    /**
     * 校验是否可以考试
     * @param paperId 试卷ID
     * @return
     */
    MessageVo checkDoExam(SharedUser sharedUser, Long paperId);

    /**
     * 学生
     * 根据试卷ID获取试题和结果
     * @param paperId
     * @return
     */
    MessageVo studentGetQuesListByPaperId(SharedUser sharedUser, Long paperId);
}
