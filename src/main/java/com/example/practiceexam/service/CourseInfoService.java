package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddCourseForm;
import com.example.practiceexam.form.UpdateCourseForm;

/**
 * @author ShiQing_Chen  2020/3/5  18:01
 **/
public interface CourseInfoService {
    /**
     * 添加
     * @param form
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddCourseForm form);

    /**
     * 修改信息
     * @param form
     * @return
     */
    MessageVo update(SharedUser sharedUser, UpdateCourseForm form);

    /**
     * 根据id删除
     * @param courseId
     * @return
     */
    MessageVo delById(Long courseId);

    /**
     * 根据id获取信息
     * @param courseId
     * @return
     */
    MessageVo getById(Long courseId);

    /**
     * 查询所有
     * 有参数则模糊查询
     * @param search
     * @return
     */
    MessageVo getList(String search);
}
