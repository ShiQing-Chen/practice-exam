package com.example.practiceexam.service;

import com.example.common.vo.MessageVo;
import com.example.practiceexam.param.SearchClassTeacherParam;

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
}
