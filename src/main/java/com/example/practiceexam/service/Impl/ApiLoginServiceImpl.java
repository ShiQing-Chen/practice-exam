package com.example.practiceexam.service.Impl;

import com.example.common.cache.SharedUser;
import com.example.common.util.JwtTokenUtils;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.config.OnlineUserManager;
import com.example.practiceexam.config.RoleManager;
import com.example.practiceexam.dao.UserInfoDao;
import com.example.practiceexam.model.UserInfo;
import com.example.practiceexam.service.ApiLoginService;
import com.example.practiceexam.vo.ApiLoginVo;
import com.example.practiceexam.vo.ApiUserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 登录登出、获取登录用户基本信息
 * @author ShiQing_Chen  2020/3/5  18:17
 **/
@Service
public class ApiLoginServiceImpl implements ApiLoginService {

    /**
     * refresh_token 30天过期
     */
    private static final int REFRESH_TOKEN_EXPIRATION = 30;

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    /**
     * 登录
     * @param request
     * @param loginText 登录账号
     * @param password  登录密码
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo login(HttpServletRequest request, String loginText, String password) {
        UserInfo user = userInfoDao.getByLoginNameOrMobile(loginText);
        if (user == null) {
            //不要给予用户过于详细的信息
            return new MessageVo(false, "用户名或密码不正确");
        }
        //帐号非正常状态
        if (!user.getUserStatus().equals(UserInfo.STATUS_ACTIVED)) {
            return new MessageVo(false, "帐号状态异常，未激活或已被冻结");
        }
        //用户名密码不匹配
        if (!passwordEncoder.matches(password, user.getLoginPass())) {
            return new MessageVo(false, "用户名或密码不正确");
        }

        //成功的逻辑
        Date now = new Date();
        //更新最后登录时间和refreshToken
        user.setLastLoginTime(now);
        String uuid = UUID.randomUUID().toString();
        user.setRefreshToken(uuid);
        userInfoDao.save(user);

        this.cacheUser(user);
        //制造jwt
        String token = JwtTokenUtils.genToken(jwtSecretKey, user.getUserId());
        String refreshToken = JwtTokenUtils.genRefreshToken(jwtSecretKey, uuid);
        ApiLoginVo vo = new ApiLoginVo();
        vo.setToken(token);
        vo.setRefreshToken(refreshToken);
        return new MessageVo(true, "成功", vo);
    }

    /**
     * 刷新token
     * @param refreshToken 刷新token
     * @return 操作结果
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo refreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            return new MessageVo(false, "刷新凭证不能为空");
        }
        String refreshUuid = JwtTokenUtils.getRefreshUuid(jwtSecretKey, refreshToken);
        if (StringUtils.isBlank(refreshUuid)) {
            return new MessageVo(false, "刷新凭证错误");
        }
        UserInfo user = userInfoDao.getByRefreshToken(refreshUuid);
        if (user == null) {
            //不要给予用户过于详细的信息
            return new MessageVo(false, "刷新凭证不正确，请重新登录");
        }
        if (!user.getUserStatus().equals(UserInfo.STATUS_ACTIVED)) {
            //帐号没有激活
            return new MessageVo(false, "帐号状态异常，未激活或已被冻结");
        }

        //判断 lastLoginTime
        Date lastLogin = user.getLastLoginTime();
        boolean needLogin = false;
        if (lastLogin == null) {
            needLogin = true;
        } else {
            DateTime now = DateTime.now();
            DateTime last = new DateTime(lastLogin);
            //计算区间天数
            Period p = new Period(last, now, PeriodType.days());
            int days = p.getDays();
            if (days > REFRESH_TOKEN_EXPIRATION) {
                needLogin = true;
            }
        }
        if (needLogin) {
            return new MessageVo(false, "距离您上次登录时间太长，请重新登录");
        }
        this.cacheUser(user);

        String token = JwtTokenUtils.genToken(jwtSecretKey, user.getUserId());
        return new MessageVo(true, "刷新成功", token);
    }

    /**
     * 退出登录
     * @param sharedUser 共享参数
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MessageVo logout(SharedUser sharedUser) {
        if (sharedUser == null || sharedUser.getUserId() == null) {
            return MessageVo.fail("登出失败");
        }
        OnlineUserManager.removeUserClient(sharedUser.getUserId(), sharedUser);
        Optional<UserInfo> userOpt = userInfoDao.findById(sharedUser.getUserId());
        if (userOpt.isPresent()) {
            UserInfo userInfo = userOpt.get();
            userInfo.setRefreshToken(null);
            userInfoDao.save(userInfo);
        }
        return MessageVo.success("登出成功");
    }

    /**
     * 用户信息详情
     * @param sharedUser 登录用户
     * @return 操作结果
     */
    @Override
    @Transactional(readOnly = true)
    public MessageVo getUserInfo(SharedUser sharedUser) {
        ApiUserInfoVo apiUserInfoVo = new ApiUserInfoVo();
        Optional<UserInfo> userInfo = userInfoDao.findById(sharedUser.getUserId());
        if (!userInfo.isPresent()) {
            return MessageVo.fail("未能找到用户信息");
        }
        BeanUtils.copyProperties(userInfo.get(), apiUserInfoVo);
        //补充角色code
        Set<String> roles = RoleManager.getRoleByType(userInfo.get().getUserType());
        apiUserInfoVo.setRoleCodes(roles);
        return MessageVo.success("成功", apiUserInfoVo);
    }

    /**
     * 将用户基本信息保存到全局
     * @param user 用户对象
     */
    private void cacheUser(UserInfo user) {
        if (user == null) {
            return;
        }
        SharedUser sharedUser = new SharedUser();
        BeanUtils.copyProperties(user, sharedUser);
        //补充角色code
        Set<String> roles = RoleManager.getRoleByType(user.getUserType());
        sharedUser.setRoleCodes(roles);
        OnlineUserManager.addUserClient(sharedUser.getUserId(), sharedUser);
    }
}
