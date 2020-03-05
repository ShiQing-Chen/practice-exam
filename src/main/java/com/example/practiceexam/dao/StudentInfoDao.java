package com.example.practiceexam.dao;

import com.example.practiceexam.model.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 学生信息dao层
 * @author ShiQing_Chen  2020/3/5  17:53
 **/
public interface StudentInfoDao extends JpaRepository<StudentInfo, Long> {
}
