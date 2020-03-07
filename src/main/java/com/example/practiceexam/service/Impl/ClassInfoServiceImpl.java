package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.ClassInfoDao;
import com.example.practiceexam.dao.TeacherInfoDao;
import com.example.practiceexam.dto.ClassDto;
import com.example.practiceexam.form.AddClassForm;
import com.example.practiceexam.form.UpdateClassForm;
import com.example.practiceexam.model.ClassInfo;
import com.example.practiceexam.model.TeacherInfo;
import com.example.practiceexam.param.SearchClassParam;
import com.example.practiceexam.service.ClassInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/5  18:04
 **/
@Service
public class ClassInfoServiceImpl implements ClassInfoService {

    @Autowired
    private ClassInfoDao classInfoDao;
    @Autowired
    private TeacherInfoDao teacherInfoDao;


    /**
     * 添加班级
     * @param classForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddClassForm classForm) {
        if (classForm != null && sharedUser != null) {
            ClassInfo classInfo = new ClassInfo();
            Date curDate = new Date();
            BeanUtils.copyProperties(classForm, classInfo);
            classInfo.setClassId(IdGeneratorUtils.getNewId());
            classInfo.setCreateUserId(sharedUser.getUserId());
            classInfo.setCreateTime(curDate);
            classInfo.setUpdateTime(curDate);
            classInfoDao.save(classInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("添加班级失败！");
    }

    /**
     * 修改班级信息
     * @param classForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo update(SharedUser sharedUser, UpdateClassForm classForm) {
        if (classForm != null && sharedUser != null) {
            ClassInfo classInfo = classInfoDao.getById(classForm.getClassId());
            if (classInfo != null) {
                Date curDate = new Date();
                BeanUtils.copyProperties(classForm, classInfo);
                classInfo.setUpdateTime(curDate);
                classInfoDao.save(classInfo);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("修改班级信息失败！");
    }

    /**
     * 根据id删除班级
     * @param classId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long classId) {
        if (classId != null) {
            int result = classInfoDao.delById(classId);
            if (result > 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除班级失败！");
    }

    /**
     * 根据id获取班级信息
     * @param classId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getById(Long classId) {
        if (classId != null) {
            ClassInfo classInfo = classInfoDao.getById(classId);
            if (classInfo != null) {
                return MessageVo.success(classInfo);
            }
        }
        return MessageVo.fail("获取班级失败！");
    }

    /**
     * 管理员
     * 分页查询
     * @param classParam
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo adminGetListByPage(SearchClassParam classParam) {
        if (classParam != null) {
            List<ClassDto> classDtoList = classInfoDao.adminGetListByPage(classParam);
            Integer count = classInfoDao.adminGetCountByPage(classParam);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", classDtoList);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }

    /**
     * 根据班级ID获取班级教师信息
     * @param classId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getClassTeacherByClassId(Long classId) {
        if (classId != null) {
            ClassInfo classInfo = classInfoDao.getById(classId);
            if (classInfo != null) {
                if (classInfo.getTeacherId() != null) {
                    TeacherInfo teacherInfo = teacherInfoDao.getById(classInfo.getTeacherId());
                    if (teacherInfo != null) {
                        return MessageVo.success(teacherInfo);
                    }
                }
                return MessageVo.success();
            }
        }
        return MessageVo.fail("获取班级失败！");
    }

    /**
     * 根据班级id获取当前教师信息
     * @param classId   班级ID
     * @param teacherId 教师ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo setClassTeacher(Long classId, Long teacherId) {
        if (classId != null && teacherId != null) {
            int result = classInfoDao.setClassTeacher(classId, teacherId);
            if (result > 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("当前班级设置教师失败！");
    }
}
