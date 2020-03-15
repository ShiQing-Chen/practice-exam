package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddQuesForm;
import com.example.practiceexam.form.UpdateQuesForm;
import com.example.practiceexam.param.GenerateSearchQuesParam;
import com.example.practiceexam.param.SearchQuesParam;

/**
 * @author ShiQing_Chen  2020/3/12  02:00
 **/
public interface QuestionInfoService {
    /**
     * 添加
     * @param quesForm
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddQuesForm quesForm);

    /**
     * 修改信息
     * @param quesForm
     * @return
     */
    MessageVo update(SharedUser sharedUser, UpdateQuesForm quesForm);

    /**
     * 根据id查询
     * @param questionId
     * @return
     */
    MessageVo getById(Long questionId);

    /**
     * 随机获取到某课程下
     * 待审核的试题
     * @param courseId 课程ID
     * @return
     */
    MessageVo getReadyReviewByCourseId(Long courseId);

    /**
     * 根据id提交审核试题
     * @param questionId
     * @return
     */
    MessageVo submit(Long questionId);

    /**
     * 根据id删除
     * @param questionId
     * @return
     */
    MessageVo delById(Long questionId);

    /**
     * 分页查询
     * @param param
     * @return
     */
    MessageVo getListByPage(SearchQuesParam param);

    /**
     * 组卷关系获取试题
     * 分页查询
     * @param param
     * @return
     */
    MessageVo generateGetListByPage(SharedUser sharedUser, GenerateSearchQuesParam param);

    /**
     * 根据试卷ID获取试题
     * @param paperId
     * @return
     */
    MessageVo getQuesListByPaperId(Long paperId);

    /**
     * 自动组卷
     * 随机获取
     * 25个选择题，5个非选择题
     * @param paperId
     * @return
     */
    MessageVo autoGetQuesList(Long paperId);

    /**
     * 教师
     * 分页查询
     * @param param
     * @return
     */
    MessageVo teacherGetListByPage(SharedUser sharedUser, SearchQuesParam param);

    /**
     * 教师
     * 随机获取到某课程下
     * 待审核的试题
     * @return
     */
    MessageVo getReadyReviewByTeacher(SharedUser sharedUser);

    /**
     * 学生
     * 根据试卷ID获取试题
     * @param paperId
     * @return
     */
    MessageVo studentGetQuesListByPaperId(Long paperId);

    /**
     * 根据试卷ID获取非选择试题
     * @param paperId
     * @return
     */
    MessageVo getSubjectiveQuesListByPaperId(Long paperId);
}
