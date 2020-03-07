package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddStudentForm;
import com.example.practiceexam.form.UpdateStudentForm;
import com.example.practiceexam.param.SearchStudentParam;

/**
 * @author ShiQing_Chen  2020/3/5  18:00
 **/
public interface StudentInfoService {
    /**
     * 添加学生
     * @param form
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddStudentForm form);

    /**
     * 修改学生信息
     * @param form
     * @return
     */
    MessageVo update(SharedUser sharedUser, UpdateStudentForm form);

    /**
     * 根据id删除学生
     * @param studentId
     * @return
     */
    MessageVo delById(Long studentId);

    /**
     * 根据id获取学生信息
     * @param studentId
     * @return
     */
    MessageVo getById(Long studentId);

    /**
     * 添加
     * 校验学号
     * @param studentNumber
     * @return
     */
    MessageVo checkStudentNumber(String studentNumber);

    /**
     * 修改
     * 校验学号
     * @param studentId
     * @param studentNumber
     * @return
     */
    MessageVo checkStudentNumberById(Long studentId, String studentNumber);
    /**
     * 分页查询
     * @param studentParam
     * @return
     */
    MessageVo getListByPage(SearchStudentParam studentParam);
}
