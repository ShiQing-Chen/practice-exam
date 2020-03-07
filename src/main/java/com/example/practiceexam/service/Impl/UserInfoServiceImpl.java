package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.dao.UserInfoDao;
import com.example.practiceexam.dto.UserDto;
import com.example.practiceexam.form.AddUserForm;
import com.example.practiceexam.form.UpdateUserForm;
import com.example.practiceexam.model.UserInfo;
import com.example.practiceexam.param.SearchUserParam;
import com.example.practiceexam.service.UserInfoService;
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
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 添加用户
     * @param addUserForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo add(SharedUser sharedUser, AddUserForm addUserForm) {
        if (sharedUser != null) {
            //查看LoginName、Mobile是否已经存在
            if (StringUtils.isNotBlank(addUserForm.getMobile())) {
                if (userInfoDao.checkLogNameAndMobile(addUserForm.getLoginName(), addUserForm.getMobile()) > 0) {
                    return MessageVo.fail("添加用户失败！用户名或手机号已被使用！");
                }
            } else {
                if (userInfoDao.checkLoginName(addUserForm.getLoginName()) > 0) {
                    return MessageVo.fail("添加用户失败！用户名已被使用！");
                }
            }
            UserInfo user = new UserInfo();
            Date curDate = new Date();
            BeanUtils.copyProperties(addUserForm, user);
            if (StringUtils.isBlank(user.getMobile())) {
                user.setMobile(null);
            }
            user.setUserId(IdGeneratorUtils.getNewId());
            if (StringUtils.isNotBlank(addUserForm.getLoginPass())) {
                user.setLoginPass(passwordEncoder.encode(addUserForm.getLoginPass()));
            }
            user.setCreateTime(curDate);
            user.setUpdateTime(curDate);
            UserInfo result = userInfoDao.save(user);
            return MessageVo.success(result);
        }
        return MessageVo.fail("添加用户失败！");
    }

    /**
     * 修改用户信息
     * @param userForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo update(SharedUser sharedUser, UpdateUserForm userForm) {
        UserInfo update = userInfoDao.getById(userForm.getUserId());
        if (sharedUser != null && update != null) {
            //查看LoginName、Mobile是否已经存在
            if (StringUtils.isNotBlank(userForm.getMobile())) {
                if (userInfoDao.checkLogNameAndMobile(userForm.getUserId(), userForm.getLoginName(), userForm.getMobile()) > 0) {
                    return MessageVo.fail("修改用户信息失败！用户名或手机号已被使用！");
                }
            } else {
                if (userInfoDao.checkLoginName(userForm.getUserId(), userForm.getLoginName()) > 0) {
                    return MessageVo.fail("修改用户信息失败！用户名已被使用！");
                }
            }
            update.setLoginName(userForm.getLoginName());
            update.setNickName(userForm.getNickName());
            update.setAvatar(userForm.getAvatar());
            update.setMobile(userForm.getMobile());
            update.setUserType(userForm.getUserType());
            if (StringUtils.isBlank(update.getMobile())) {
                update.setMobile(null);
            }
            UserInfo result = userInfoDao.save(update);
            return MessageVo.success(result);
        }
        return MessageVo.fail("修改用户信息失败！");
    }

    /**
     * 重置用户密码
     * 重置为111111
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo resetPassword(Long userId) {
        if (userId != null) {
            UserInfo update = userInfoDao.getById(userId);
            if (update != null) {
                // 密码重置为111111
                update.setLoginPass(passwordEncoder.encode("111111"));
                userInfoDao.save(update);
                return MessageVo.success("该用户密码重置成功！重置为：111111");
            }
        }
        return MessageVo.fail("重置密码失败！");
    }

    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getById(Long userId) {
        if (userId != null) {
            UserInfo user = userInfoDao.getById(userId);
            if (user != null) {
                return MessageVo.success(user);
            }
        }
        return MessageVo.fail("获取用户失败！");
    }

    /**
     * 根据id删除用户
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo delById(Long userId) {
        if (userId != null) {
            int result = userInfoDao.delById(userId);
            if (result > 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("删除用户失败！");
    }

    /**
     * 根据id禁用用户
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo disableById(Long userId) {
        if (userId != null) {
            userInfoDao.disableById(userId);
            return MessageVo.success("禁用用户成功！");
        }
        return MessageVo.fail("禁用用户失败！");
    }

    /**
     * 根据id启用用户
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo enableById(Long userId) {
        if (userId != null) {
            userInfoDao.enableById(userId);
            return MessageVo.success("启用用户成功！");
        }
        return MessageVo.fail("启用用户失败！");
    }

    /**
     * 获取用户详细信息
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getInfoById(Long userId) {
        if (userId != null) {
            UserInfo user = userInfoDao.getById(userId);
            if (user != null) {
                return MessageVo.success(user);
            }
        }
        return MessageVo.fail("获取用户信息失败！");
    }

    /**
     * 添加
     * 校验手机号
     * @param mobile
     * @return
     */
    @Override
    public MessageVo checkMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            if (userInfoDao.checkMobile(mobile) == 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("手机号校验失败！");
    }

    /**
     * 修改
     * 校验手机号
     * @param mobile
     * @return
     */
    @Override
    public MessageVo checkMobileById(Long userId, String mobile) {
        if (StringUtils.isNotBlank(mobile) && userId != null) {
            if (userInfoDao.checkMobile(userId, mobile) == 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("手机号校验失败！");
    }

    /**
     * 添加
     * 校验登录账号
     * @param loginName
     * @return
     */
    @Override
    public MessageVo checkLoginName(String loginName) {
        if (StringUtils.isNotBlank(loginName)) {
            if (userInfoDao.checkLoginName(loginName) == 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("登录账号校验失败！");
    }

    /**
     * 修改
     * 校验登录账号
     * @param userId
     * @param loginName
     * @return
     */
    @Override
    public MessageVo checkLoginNameById(Long userId, String loginName) {
        if (StringUtils.isNotBlank(loginName) && userId != null) {
            if (userInfoDao.checkLoginName(userId, loginName) == 0) {
                return MessageVo.success();
            }
        }
        return MessageVo.fail("登录账号校验失败！");
    }

    /**
     * 更换头像
     * @param userId
     * @param avatar
     * @return
     */
    @Override
    public MessageVo changeAvatar(Long userId, String avatar) {
        if (StringUtils.isNotBlank(avatar) && userId != null) {
            UserInfo update = userInfoDao.getById(userId);
            if (update != null) {
                update.setAvatar(avatar);
                userInfoDao.save(update);
                return MessageVo.success();
            }
        }
        return MessageVo.fail("更换头像失败！");
    }

    /**
     * 分页查询
     * @param userParam
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo adminGetListByPage(SearchUserParam userParam) {
        if (userParam != null) {
            List<UserDto> userInfoList = userInfoDao.adminGetListByPage(userParam);
            Integer count = userInfoDao.adminGetCountByPage(userParam);
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", userInfoList);
            map.put("total", count);
            return MessageVo.success(map);
        } else {
            return MessageVo.success(Lists.newArrayList());
        }
    }
}
