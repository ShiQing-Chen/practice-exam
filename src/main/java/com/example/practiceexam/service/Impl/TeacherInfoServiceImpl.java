package com.example.practiceexam.service.Impl;

import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.TeacherInfoDao;
import com.example.practiceexam.dto.ClassTeacherDto;
import com.example.practiceexam.param.SearchClassTeacherParam;
import com.example.practiceexam.service.TeacherInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/5  18:02
 **/
@Service
public class TeacherInfoServiceImpl implements TeacherInfoService {

    @Autowired
    private TeacherInfoDao teacherInfoDao;

    /**
     * 班级设置教师
     * 获取教师列表
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo classGetListByPage(SearchClassTeacherParam param) {
        if (param != null) {
            List<ClassTeacherDto> teacherDtos = teacherInfoDao.classGetListByPage(param);
            Integer count = teacherInfoDao.classGetCountByPage(param);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", teacherDtos);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
