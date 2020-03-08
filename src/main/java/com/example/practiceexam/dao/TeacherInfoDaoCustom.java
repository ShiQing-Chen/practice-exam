package com.example.practiceexam.dao;

import com.example.practiceexam.dto.ClassTeacherDto;
import com.example.practiceexam.dto.TeacherDto;
import com.example.practiceexam.param.SearchClassTeacherParam;
import com.example.practiceexam.param.SearchTeacherParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/7  17:49
 **/
@Repository
public interface TeacherInfoDaoCustom {

    /**
     * 班级设置教师
     * 获取教师列表
     * 分页查询
     * @param param
     * @return
     */
    List<ClassTeacherDto> classGetListByPage(SearchClassTeacherParam param);

    /**
     * 班级设置教师
     * 获取教师列表
     * 分页查询
     * @param param
     * @return
     */
    Integer classGetCountByPage(SearchClassTeacherParam param);

    /**
     * 分页查询
     * @param param
     * @return
     */
    List<TeacherDto> getListByPage(SearchTeacherParam param);

    /**
     * 分页查询
     * @param param
     * @return
     */
    Integer getCountByPage(SearchTeacherParam param);
}
