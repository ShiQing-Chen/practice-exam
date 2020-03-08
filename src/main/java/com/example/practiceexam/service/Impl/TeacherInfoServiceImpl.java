package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.TeacherInfoDao;
import com.example.practiceexam.dao.UserInfoDao;
import com.example.practiceexam.dto.ClassTeacherDto;
import com.example.practiceexam.dto.TeacherDto;
import com.example.practiceexam.dto.TeacherInfoDto;
import com.example.practiceexam.form.AddTeacherForm;
import com.example.practiceexam.form.UpdateTeacherForm;
import com.example.practiceexam.model.TeacherInfo;
import com.example.practiceexam.model.UserInfo;
import com.example.practiceexam.param.SearchClassTeacherParam;
import com.example.practiceexam.param.SearchTeacherParam;
import com.example.practiceexam.service.TeacherInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/5  18:02
 **/
@Service
public class TeacherInfoServiceImpl implements TeacherInfoService {

    @Autowired
    private TeacherInfoDao teacherInfoDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


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

    /**
     * 添加教师
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddTeacherForm form) {
        if (sharedUser != null && form != null) {
            //查看学号是否已经存在
            if (teacherInfoDao.checkTeacherNumber(form.getTeacherNumber()) > 0) {
                return MessageVo.fail("添加教师失败！工号已被使用！");
            }
            // 校验学号是否可以作为登录账号
            if (userInfoDao.checkLoginName(form.getTeacherNumber()) > 0) {
                return MessageVo.fail("添加教师失败！工号已被其他用户使用为登录账号，请删除用户后重试！");
            }
            Date curDate = new Date();
            // 用户信息
            UserInfo user = new UserInfo();
            user.setUserId(IdGeneratorUtils.getNewId());
            user.setNickName(form.getTeacherName());
            user.setLoginName(form.getTeacherNumber());
            user.setLoginPass(passwordEncoder.encode(form.getTeacherNumber()));
            user.setUserType(UserInfo.TYPE_TEACHER);
            user.setGender(form.getGender());
            user.setCreateTime(curDate);
            user.setUpdateTime(curDate);
            userInfoDao.save(user);

            // 学生信息
            TeacherInfo teacherInfo = new TeacherInfo();
            BeanUtils.copyProperties(form, teacherInfo);
            teacherInfo.setTeacherId(IdGeneratorUtils.getNewId());
            teacherInfo.setCreateTime(curDate);
            teacherInfo.setUpdateTime(curDate);
            teacherInfo.setCreateUserId(sharedUser.getUserId());
            teacherInfo.setUserId(user.getUserId());
            teacherInfoDao.save(teacherInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("添加教师失败！");
    }

    /**
     * 修改教师信息
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo update(SharedUser sharedUser, UpdateTeacherForm form) {
        if (sharedUser != null && form != null) {
            TeacherInfo teacherInfo = teacherInfoDao.getById(form.getTeacherId());
            if (teacherInfo == null) {
                return MessageVo.fail("未查询到需要修改教师信息");
            }
            //查看工号是否已经存在
            if (teacherInfoDao.checkTeacherNumber(form.getTeacherId(), form.getTeacherNumber()) > 0) {
                return MessageVo.fail("修改教师失败！工号已被使用！");
            }
            // 校验工号是否可以作为登录账号
            if (userInfoDao.checkLoginName(teacherInfo.getUserId(), form.getTeacherNumber()) > 0) {
                return MessageVo.fail("修改教师失败！工号已被其他用户使用为登录账号，请删除用户后重试！");
            }
            teacherInfo.setTeacherNumber(form.getTeacherNumber());
            teacherInfo.setTeacherName(form.getTeacherName());
            teacherInfoDao.save(teacherInfo);

            Date curDate = new Date();
            // 用户信息
            UserInfo newUser = new UserInfo();
            if (teacherInfo.getUserId() != null) {
                UserInfo userInfo = userInfoDao.getById(teacherInfo.getUserId());
                if (userInfo != null) {
                    userInfo.setGender(form.getGender());
                    userInfoDao.save(userInfo);
                    return MessageVo.success();
                } else {
                    newUser.setUserId(teacherInfo.getUserId());
                }
            } else {
                newUser.setUserId(IdGeneratorUtils.getNewId());
            }
            newUser.setNickName(teacherInfo.getTeacherName());
            newUser.setLoginName(teacherInfo.getTeacherNumber());
            newUser.setLoginPass(passwordEncoder.encode(form.getTeacherNumber()));
            newUser.setUserType(UserInfo.TYPE_STUDENT);
            newUser.setGender(form.getGender());
            newUser.setCreateTime(curDate);
            newUser.setUpdateTime(curDate);
            userInfoDao.save(newUser);
            return MessageVo.success();
        }
        return MessageVo.fail("修改教师信息失败！");
    }

    /**
     * 根据id删除教师
     * @param teacherId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long teacherId) {
        // 删除教师同时删除用户
        if (teacherId != null) {
            TeacherInfo teacherInfo = teacherInfoDao.getById(teacherId);
            if (teacherInfo != null) {
                Long userId = teacherInfo.getUserId();
                teacherInfoDao.delById(teacherId);
                if (userId != null) {
                    userInfoDao.delById(userId);
                }
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除教师失败！");
    }

    /**
     * 根据id获取教师信息
     * @param teacherId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getById(Long teacherId) {
        if (teacherId != null) {
            TeacherInfo teacherInfo = teacherInfoDao.getById(teacherId);
            if (teacherInfo != null) {
                TeacherInfoDto teacherInfoDto = new TeacherInfoDto();
                BeanUtils.copyProperties(teacherInfo, teacherInfoDto);
                // 教师信息附带用户信息
                Long userId = teacherInfo.getUserId();
                if (userId != null) {
                    UserInfo userInfo = userInfoDao.getById(userId);
                    if (userInfo != null) {
                        teacherInfoDto.setAvatar(userInfo.getAvatar());
                        teacherInfoDto.setUserType(userInfo.getUserType());
                        teacherInfoDto.setGender(userInfo.getGender());
                        teacherInfoDto.setMobile(userInfo.getMobile());
                    }
                }
                return MessageVo.success(teacherInfoDto);
            }
        }
        return MessageVo.fail("获取教师信息失败！");
    }

    /**
     * 添加
     * 校验工号
     * @param teacherNumber
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo checkTeacherNumber(String teacherNumber) {
        if (StringUtils.isNotBlank(teacherNumber)) {
            if (teacherInfoDao.checkTeacherNumber(teacherNumber) == 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("工号校验失败！");
    }

    /**
     * 修改
     * 校验工号
     * @param teacherId
     * @param teacherNumber
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo checkTeacherNumberById(Long teacherId, String teacherNumber) {
        if (StringUtils.isNotBlank(teacherNumber) && teacherId != null) {
            if (teacherInfoDao.checkTeacherNumber(teacherId, teacherNumber) == 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("工号校验失败！");
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SearchTeacherParam param) {
        if (param != null) {
            List<TeacherDto> teacherDtoList = teacherInfoDao.getListByPage(param);
            Integer count = teacherInfoDao.getCountByPage(param);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", teacherDtoList);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
