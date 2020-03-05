package com.example.practiceexam.dao;

import com.example.practiceexam.model.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 教师信息dao层
 * @author ShiQing_Chen  2020/3/5  17:52
 **/
public interface TeacherInfoDao extends JpaRepository<TeacherInfo, Long> {
}
