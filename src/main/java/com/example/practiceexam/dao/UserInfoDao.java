package com.example.practiceexam.dao;

import com.example.practiceexam.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户信息dao层
 * @author ShiQing_Chen  2020/3/5  17:51
 **/
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {

    @Query(value = "select u.* from user_info u where u.login_name = ?1 or u.mobile = ?1",nativeQuery = true)
    UserInfo getByLoginNameOrMobile(String loginText);

    @Query(value = "select u.* from user_info u where u.refresh_token = ?1 ",nativeQuery = true)
    UserInfo getByRefreshToken(String refreshToken);

}
