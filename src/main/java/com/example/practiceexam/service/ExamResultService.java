package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddExamResultForm;
import com.example.practiceexam.form.AddMarkExamResultForm;

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

    /**
     * 获取学生 答题结果
     * 根据试卷ID、学生ID获取试题
     * @param paperId   试卷ID
     * @param studentId 学生ID
     * @return
     */
    MessageVo getQuesListByPaperIdAndStudentId(Long paperId, Long studentId);

    /**
     * 批改
     * 根据试卷ID和试题ID随机获取未批改的结果
     * @param paperId
     * @return
     */
    MessageVo getResultByPaperIdAndQuesId(Long paperId, Long questionId);

    /**
     * 教师批改提交
     * @param sharedUser
     * @param form
     * @return
     */
    MessageVo markAdd(SharedUser sharedUser, AddMarkExamResultForm form);
}
