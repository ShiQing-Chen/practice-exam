package com.example.practiceexam.service;

import com.example.common.cache.SharedUser;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddClassForm;
import com.example.practiceexam.form.UpdateClassForm;
import com.example.practiceexam.param.SearchClassParam;

/**
 * @author ShiQing_Chen  2020/3/5  18:01
 **/
public interface ClassInfoService {
    /**
     * 添加班级
     * @param classForm
     * @return
     */
    MessageVo add(SharedUser sharedUser, AddClassForm classForm);

    /**
     * 修改班级信息
     * @param classForm
     * @return
     */
    MessageVo update(SharedUser sharedUser, UpdateClassForm classForm);

    /**
     * 根据id删除班级
     * @param classId
     * @return
     */
    MessageVo delById(Long classId);

    /**
     * 根据id获取班级信息
     * @param classId
     * @return
     */
    MessageVo getById(Long classId);

    /**
     * 管理员
     * 分页查询
     * @param classParam
     * @return
     */
    MessageVo adminGetListByPage(SearchClassParam classParam);

    /**
     * 根据班级ID获取班级教师信息
     * @param classId
     * @return
     */
    MessageVo getClassTeacherByClassId(Long classId);

    /**
     * 根据班级id获取当前教师信息
     * @param classId   班级ID
     * @param teacherId 教师ID
     * @return
     */
    MessageVo setClassTeacher(Long classId, Long teacherId);

    /**
     * 获取专业名称列表、班级名称列表
     * @return
     */
    MessageVo getListClassAndMajor();

    /**
     * 远程模糊查询班级信息
     * @return
     */
    MessageVo searchListClassName(String search);

    /**
     * 学生编辑初始化学生班级信息
     * @return
     */
    MessageVo initStudentClassById(Long classId);

    /**
     * 获取班级列表
     * @return
     */
    MessageVo getListClassIdName();
}
