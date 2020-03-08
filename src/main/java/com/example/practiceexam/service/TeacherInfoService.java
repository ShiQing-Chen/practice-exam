package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddTeacherForm;
import com.example.practiceexam.form.UpdateTeacherForm;
import com.example.practiceexam.param.SearchClassTeacherParam;
import com.example.practiceexam.param.SearchTeacherParam;

/**
 * @author ShiQing_Chen  2020/3/5  18:00
 **/
public interface TeacherInfoService {
    /**
     * 班级设置教师
     * 获取教师列表
     * 分页查询
     * @param param
     * @return
     */
    MessageVo classGetListByPage(SearchClassTeacherParam param);

    /**
     * 添加教师
     * @param form
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddTeacherForm form);

    /**
     * 修改教师信息
     * @param form
     * @return
     */
    MessageVo update(SharedUser sharedUser, UpdateTeacherForm form);

    /**
     * 根据id删除教师
     * @param teacherId
     * @return
     */
    MessageVo delById(Long teacherId);

    /**
     * 根据id获取教师信息
     * @param teacherId
     * @return
     */
    MessageVo getById(Long teacherId);

    /**
     * 添加
     * 校验工号
     * @param teacherNumber
     * @return
     */
    MessageVo checkTeacherNumber(String teacherNumber);

    /**
     * 修改
     * 校验工号
     * @param teacherId
     * @param teacherNumber
     * @return
     */
    MessageVo checkTeacherNumberById(Long teacherId, String teacherNumber);

    /**
     * 分页查询
     * @param param
     * @return
     */
    MessageVo getListByPage(SearchTeacherParam param);
}
