package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.ClassInfoDao;
import com.example.practiceexam.dao.PaperClassDao;
import com.example.practiceexam.dao.StudentInfoDao;
import com.example.practiceexam.dao.UserInfoDao;
import com.example.practiceexam.dto.StudentDto;
import com.example.practiceexam.dto.StudentInfoDto;
import com.example.practiceexam.dto.StudentScoreDto;
import com.example.practiceexam.form.AddStudentForm;
import com.example.practiceexam.form.UpdateStudentForm;
import com.example.practiceexam.model.StudentInfo;
import com.example.practiceexam.model.TeacherInfo;
import com.example.practiceexam.model.UserInfo;
import com.example.practiceexam.param.ScoreSearchStudentParam;
import com.example.practiceexam.param.SearchStudentParam;
import com.example.practiceexam.service.StudentInfoService;
import com.example.practiceexam.vo.AddStudentVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/5  18:03
 **/
@Service
public class StudentInfoServiceImpl implements StudentInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private StudentInfoDao studentInfoDao;

    @Autowired
    private ClassInfoDao classInfoDao;

    @Autowired
    private PaperClassDao paperClassDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 添加学生
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddStudentForm form) {
        if (sharedUser != null && form != null) {
            //查看学号是否已经存在
            if (studentInfoDao.checkStudentNumber(form.getStudentNumber()) > 0) {
                return MessageVo.fail("添加学生失败！学号已被使用！");
            }
            // 校验学号是否可以作为登录账号
            if (userInfoDao.checkLoginName(form.getStudentNumber()) > 0) {
                return MessageVo.fail("添加学生失败！学号已被其他用户使用为登录账号，请删除用户后重试！");
            }
            Date curDate = new Date();
            // 用户信息
            UserInfo user = new UserInfo();
            user.setUserId(IdGeneratorUtils.getNewId());
            user.setNickName(form.getStudentName());
            user.setLoginName(form.getStudentNumber());
            user.setLoginPass(passwordEncoder.encode(form.getStudentNumber()));
            user.setUserType(UserInfo.TYPE_STUDENT);
            user.setGender(form.getGender());
            user.setCreateTime(curDate);
            user.setUpdateTime(curDate);
            userInfoDao.save(user);

            // 学生信息
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(form, studentInfo);
            studentInfo.setStudentId(IdGeneratorUtils.getNewId());
            studentInfo.setCreateTime(curDate);
            studentInfo.setUpdateTime(curDate);
            studentInfo.setCreateUserId(sharedUser.getUserId());
            studentInfo.setUserId(user.getUserId());
            studentInfoDao.save(studentInfo);
            return MessageVo.success();
        }
        return MessageVo.fail("添加学生失败！");
    }

    /**
     * 修改学生信息
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo update(SharedUser sharedUser, UpdateStudentForm form) {
        if (sharedUser != null && form != null) {
            StudentInfo studentInfo = studentInfoDao.getById(form.getStudentId());
            if (studentInfo == null) {
                return MessageVo.fail("未查询到需要修改学生信息");
            }
            //查看学号是否已经存在
            if (studentInfoDao.checkStudentNumber(form.getStudentId(), form.getStudentNumber()) > 0) {
                return MessageVo.fail("修改学生失败！学号已被使用！");
            }
            // 校验学号是否可以作为登录账号
            if (userInfoDao.checkLoginName(studentInfo.getUserId(), form.getStudentNumber()) > 0) {
                return MessageVo.fail("修改学生失败！学号已被其他用户使用为登录账号，请删除用户后重试！");
            }
            studentInfo.setStudentNumber(form.getStudentNumber());
            studentInfo.setStudentName(form.getStudentName());
            studentInfoDao.save(studentInfo);

            Date curDate = new Date();
            // 用户信息
            UserInfo newUser = new UserInfo();
            if (studentInfo.getUserId() != null) {
                UserInfo userInfo = userInfoDao.getById(studentInfo.getUserId());
                if (userInfo != null) {
                    userInfo.setGender(form.getGender());
                    userInfoDao.save(userInfo);
                    return MessageVo.success();
                } else {
                    newUser.setUserId(studentInfo.getUserId());
                }
            } else {
                newUser.setUserId(IdGeneratorUtils.getNewId());
            }
            newUser.setNickName(studentInfo.getStudentName());
            newUser.setLoginName(studentInfo.getStudentNumber());
            newUser.setLoginPass(passwordEncoder.encode(form.getStudentNumber()));
            newUser.setUserType(UserInfo.TYPE_STUDENT);
            newUser.setGender(form.getGender());
            newUser.setCreateTime(curDate);
            newUser.setUpdateTime(curDate);
            userInfoDao.save(newUser);
            return MessageVo.success();
        }
        return MessageVo.fail("修改学生信息失败！");
    }

    /**
     * 根据id删除学生
     * @param studentId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long studentId) {
        // 删除学生同时删除用户
        if (studentId != null) {
            StudentInfo studentInfo = studentInfoDao.getById(studentId);
            if (studentInfo != null) {
                Long userId = studentInfo.getUserId();
                studentInfoDao.delById(studentId);
                if (userId != null) {
                    userInfoDao.delById(userId);
                }
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除学生失败！");
    }

    /**
     * 根据id获取学生信息
     * @param studentId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getById(Long studentId) {
        if (studentId != null) {
            StudentInfo studentInfo = studentInfoDao.getById(studentId);
            if (studentInfo != null) {
                StudentInfoDto studentInfoDto = new StudentInfoDto();
                BeanUtils.copyProperties(studentInfo, studentInfoDto);
                // 学生信息附带用户信息
                Long userId = studentInfo.getUserId();
                if (userId != null) {
                    UserInfo userInfo = userInfoDao.getById(userId);
                    if (userInfo != null) {
                        studentInfoDto.setAvatar(userInfo.getAvatar());
                        studentInfoDto.setUserType(userInfo.getUserType());
                        studentInfoDto.setGender(userInfo.getGender());
                        studentInfoDto.setMobile(userInfo.getMobile());
                    }
                }
                return MessageVo.success(studentInfoDto);
            }
        }
        return MessageVo.fail("获取学生信息失败！");
    }

    /**
     * 添加
     * 校验学号
     * @param studentNumber
     * @return
     */
    @Override
    public MessageVo checkStudentNumber(String studentNumber) {
        if (StringUtils.isNotBlank(studentNumber)) {
            if (studentInfoDao.checkStudentNumber(studentNumber) == 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("学号校验失败！");
    }

    /**
     * 修改
     * 校验学号
     * @param studentId
     * @param studentNumber
     * @return
     */
    @Override
    public MessageVo checkStudentNumberById(Long studentId, String studentNumber) {
        if (StringUtils.isNotBlank(studentNumber) && studentId != null) {
            if (studentInfoDao.checkStudentNumber(studentId, studentNumber) == 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("学号校验失败！");
    }

    /**
     * 分页查询
     * @param studentParam
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getListByPage(SearchStudentParam studentParam) {
        if (studentParam != null) {
            List<StudentDto> userInfoList = studentInfoDao.getListByPage(studentParam);
            Integer count = studentInfoDao.getCountByPage(studentParam);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", userInfoList);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }

    /**
     * 批量添加学生
     * @param studentVoList
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo addSome(SharedUser sharedUser, List<AddStudentVo> studentVoList) {
        if (sharedUser != null && !CollectionUtils.isEmpty(studentVoList)) {
            List<UserInfo> userList = Lists.newArrayList();
            List<StudentInfo> studentList = Lists.newArrayList();
            List<String> errList = Lists.newArrayList();
            for (AddStudentVo studentVo : studentVoList) {
                //查看学号是否已经存在
                if (studentInfoDao.checkStudentNumber(studentVo.getStudentNumber()) > 0) {
                    errList.add("（" + studentVo.getStudentNumber() + "学号已被使用）");
                    continue;
                }
                // 校验学号是否可以作为登录账号
                if (userInfoDao.checkLoginName(studentVo.getStudentNumber()) > 0) {
                    errList.add("（" + studentVo.getStudentNumber() + "学号已被使用为登录账号）");
                    continue;
                }
                Date curDate = new Date();
                // 用户信息
                UserInfo user = new UserInfo();
                user.setUserId(IdGeneratorUtils.getNewId());
                user.setNickName(studentVo.getStudentName());
                user.setLoginName(studentVo.getStudentNumber());
                user.setLoginPass(passwordEncoder.encode(studentVo.getStudentNumber()));
                user.setUserType(UserInfo.TYPE_STUDENT);
                user.setGender(studentVo.getGender());
                user.setCreateTime(curDate);
                user.setUpdateTime(curDate);
                userList.add(user);
                // 学生信息
                StudentInfo studentInfo = new StudentInfo();
                BeanUtils.copyProperties(studentVo, studentInfo);
                studentInfo.setStudentId(IdGeneratorUtils.getNewId());
                studentInfo.setCreateTime(curDate);
                studentInfo.setUpdateTime(curDate);
                studentInfo.setCreateUserId(sharedUser.getUserId());
                studentInfo.setUserId(user.getUserId());
                studentList.add(studentInfo);
            }
            if (!CollectionUtils.isEmpty(userList)) {
                userInfoDao.saveAll(userList);
            }
            if (!CollectionUtils.isEmpty(studentList)) {
                studentInfoDao.saveAll(studentList);
            }
            return MessageVo.success(errList);
        }
        return MessageVo.fail("导入失败！");
    }

    /**
     * 教师查询
     * 分页查询
     * @param studentParam
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo teacherGetListByPage(SharedUser sharedUser, SearchStudentParam studentParam) {
        if (studentParam != null) {
            if (sharedUser.getTeacherId() == null) {
                return MessageVo.fail("班级数据获取失败，未查询到当前用户的教师信息！");
            }
            List<Long> classIdList = classInfoDao.getClassIdByTeacherId(sharedUser.getTeacherId());
            if (CollectionUtils.isEmpty(classIdList)) {
                return MessageVo.fail("当前教师未带任何班级！");
            }
            studentParam.setClassIdList(classIdList);
            List<StudentDto> userInfoList = studentInfoDao.getListByPage(studentParam);
            Integer count = studentInfoDao.getCountByPage(studentParam);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", userInfoList);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }

    /**
     * 根据试卷ID获取学生分数
     * 分页查询
     * @param studentParam
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo scoreGetListByPage(SharedUser sharedUser, ScoreSearchStudentParam studentParam) {
        if (studentParam != null) {
            List<Long> classIdList = paperClassDao.getClassIdsByPaperId(studentParam.getPaperId());
            if (CollectionUtils.isEmpty(classIdList)) {
                return MessageVo.fail("当前试卷未分发给任何班级！");
            }
            studentParam.setClassIdList(classIdList);
            List<StudentScoreDto> userInfoList = studentInfoDao.scoreGetListByPage(studentParam);
            Integer count = studentInfoDao.scoreGetCountByPage(studentParam);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", userInfoList);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
