package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddQuesForm;
import com.example.practiceexam.form.UpdateQuesForm;
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
}
