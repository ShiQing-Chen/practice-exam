package com.example.practiceexam.dao;

import com.example.practiceexam.model.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 课程信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:55
 **/
public interface CourseInfoDao extends JpaRepository<CourseInfo, Long> {
}
