package com.example.practiceexam.dao;

import com.example.practiceexam.model.SystemMessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 系统消息记录Dao
 * @author ShiQing_Chen  2020/3/11  19:34
 **/
public interface SystemMessageLogDao extends JpaRepository<SystemMessageLog, Long>, SystemMessageLogDaoCustom{
}
