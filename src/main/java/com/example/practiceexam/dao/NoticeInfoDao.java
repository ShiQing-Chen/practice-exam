package com.example.practiceexam.dao;

import com.example.practiceexam.model.NoticeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 通知信息dao层
 * @author ShiQing_Chen  2020/3/5  17:53
 **/
public interface NoticeInfoDao extends JpaRepository<NoticeInfo, Long> {
}