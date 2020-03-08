package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.CourseInfoDao;
import com.example.practiceexam.form.AddCourseForm;
import com.example.practiceexam.form.UpdateCourseForm;
import com.example.practiceexam.model.CourseInfo;
import com.example.practiceexam.service.CourseInfoService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/5  18:04
 **/
@Service
public class CourseInfoServiceImpl implements CourseInfoService {

    @Autowired
    private CourseInfoDao courseInfoDao;

    /**
     * 添加
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddCourseForm form) {
        if (form != null && sharedUser != null) {
            CourseInfo courseInfo = new CourseInfo();
            Date curDate = new Date();
            BeanUtils.copyProperties(form, courseInfo);
            courseInfo.setCourseId(IdGeneratorUtils.getNewId());
            courseInfo.setCreateUserId(sharedUser.getUserId());
            courseInfo.setCreateTime(curDate);
            courseInfo.setUpdateTime(curDate);
            courseInfoDao.save(courseInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("添加课程失败！");
    }

    /**
     * 修改信息
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo update(SharedUser sharedUser, UpdateCourseForm form) {
        if (form != null && sharedUser != null) {
            CourseInfo courseInfo = courseInfoDao.getById(form.getCourseId());
            if (courseInfo != null) {
                Date curDate = new Date();
                BeanUtils.copyProperties(form, courseInfo);
                courseInfo.setUpdateTime(curDate);
                courseInfoDao.save(courseInfo);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("修改课程失败！");
    }

    /**
     * 根据id删除
     * @param courseId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long courseId) {
        if (courseId != null) {
            int result = courseInfoDao.delById(courseId);
            if (result > 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除课程失败！");
    }

    /**
     * 根据id获取信息
     * @param courseId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getById(Long courseId) {
        if (courseId != null) {
            CourseInfo courseInfo = courseInfoDao.getById(courseId);
            if (courseInfo != null) {
                return MessageVo.success(courseInfo);
            }
        }
        return MessageVo.fail("获取资料失败！");
    }

    /**
     * 查询所有
     * 有参数则模糊查询
     * @param search
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getList(String search) {
        List<CourseInfo> list;
        if (StringUtils.isBlank(search)) {
            list = courseInfoDao.getList();
        } else {
            list = courseInfoDao.getList(search);
        }
        if (list == null) {
            return MessageVo.success(Lists.newArrayList());
        } else {
            return MessageVo.success(list);
        }
    }
}
