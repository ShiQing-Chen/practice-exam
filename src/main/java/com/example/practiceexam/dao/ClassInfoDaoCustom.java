package com.example.practiceexam.dao;

import com.example.practiceexam.dto.ClassDto;
import com.example.practiceexam.dto.ValueLabelDto;
import com.example.practiceexam.param.SearchClassParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 班级信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:55
 **/
@Repository
public interface ClassInfoDaoCustom {
    /**
     * 分页查询
     * @param classParam
     * @return
     */
    List<ClassDto> getListByPage(SearchClassParam classParam);

    /**
     * 分页查询
     * @param classParam
     * @return
     */
    Integer getCountByPage(SearchClassParam classParam);

    /**
     * 模糊查询班级
     * @return
     */
    List<ValueLabelDto> searchListClassName(String search);

    /**
     * 学生编辑初始化学生班级信息
     * @return
     */
    List<ValueLabelDto> initStudentClassById(Long classId);

    /**
     * 获取班级列表
     * @return
     */
    List<ValueLabelDto> getListClassIdName();

    /**
     * 教师
     * 获取班级列表
     * @return
     */
    List<ValueLabelDto> teacherGetListClassIdName(Long teacherId);
}
