package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.ClassInfoDao;
import com.example.practiceexam.dao.TeacherInfoDao;
import com.example.practiceexam.dto.ClassDto;
import com.example.practiceexam.dto.ValueLabelDto;
import com.example.practiceexam.form.AddClassForm;
import com.example.practiceexam.form.UpdateClassForm;
import com.example.practiceexam.model.ClassInfo;
import com.example.practiceexam.model.TeacherInfo;
import com.example.practiceexam.param.SearchClassParam;
import com.example.practiceexam.service.ClassInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
            List<ClassDto> classDtoList = classInfoDao.getListByPage(classParam);
            Integer count = classInfoDao.getCountByPage(classParam);
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

    /**
     * 获取专业名称列表、班级名称列表
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListClassAndMajor() {
        List<String> classNameList = classInfoDao.getListClassName();
        List<String> majorNameList = classInfoDao.getListMajorName();
        Map<String, Object> map = Maps.newHashMap();
        if (CollectionUtils.isEmpty(classNameList)) {
            classNameList = Lists.newArrayList();
        }
        if (CollectionUtils.isEmpty(majorNameList)) {
            majorNameList = Lists.newArrayList();
        }
        map.put("classNameList", classNameList);
        map.put("majorNameList", majorNameList);
        return MessageVo.success(map);
    }

    /**
     * 远程模糊查询班级信息
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo searchListClassName(String search) {
        if (StringUtils.isNotBlank(search)) {
            List<ValueLabelDto> dtos = classInfoDao.searchListClassName(search);
            return MessageVo.success(dtos);
        }
        return MessageVo.success(Lists.newArrayList());
    }

    /**
     * 学生编辑初始化学生班级信息
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo initStudentClassById(Long classId) {
        if (classId != null) {
            List<ValueLabelDto> dtos = classInfoDao.initStudentClassById(classId);
            return MessageVo.success(dtos);
        }
        return MessageVo.success(Lists.newArrayList());
    }

    /**
     * 获取班级列表
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListClassIdName() {
        List<ValueLabelDto> dtos = classInfoDao.getListClassIdName();
        return MessageVo.success(dtos);
    }

    /**
     * 教师
     * 分页查询
     * @param classParam
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo teacherGetListByPage(SharedUser sharedUser, SearchClassParam classParam) {
        if (classParam != null && sharedUser != null) {
            if (sharedUser.getTeacherId() == null) {
                return MessageVo.fail("班级数据获取失败，未查询到当前用户的教师信息！");
            }
            classParam.setTeacherId(sharedUser.getTeacherId());
            List<ClassDto> classDtoList = classInfoDao.getListByPage(classParam);
            Integer count = classInfoDao.getCountByPage(classParam);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", classDtoList);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }

    /**
     * 教师
     * 添加班级
     * @param classForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo addByTeacher(SharedUser sharedUser, AddClassForm classForm) {
        if (classForm != null && sharedUser != null) {
            ClassInfo classInfo = new ClassInfo();
            Date curDate = new Date();
            BeanUtils.copyProperties(classForm, classInfo);
            classInfo.setClassId(IdGeneratorUtils.getNewId());
            classInfo.setCreateUserId(sharedUser.getUserId());
            classInfo.setCreateTime(curDate);
            classInfo.setUpdateTime(curDate);
            if (sharedUser.getTeacherId() == null) {
                return MessageVo.fail("班级数据获取失败，未查询到当前用户的教师信息！");
            }
            classInfo.setTeacherId(sharedUser.getTeacherId());
            classInfoDao.save(classInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("添加班级失败！");
    }
}
