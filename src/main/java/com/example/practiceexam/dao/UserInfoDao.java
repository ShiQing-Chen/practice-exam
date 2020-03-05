package com.example.practiceexam.dao;

import com.example.practiceexam.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户信息dao层
 * @author ShiQing_Chen  2020/3/5  17:51
 **/
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {

}
