package com.example.practiceexam.dao;

import com.example.practiceexam.model.ClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 班级信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:55
 **/
public interface ClassInfoDao extends JpaRepository<ClassInfo, Long> {
}
