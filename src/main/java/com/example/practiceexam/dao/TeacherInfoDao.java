package com.example.practiceexam.dao;

import com.example.practiceexam.model.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 教师信息dao层
 * @author ShiQing_Chen  2020/3/5  17:52
 **/
public interface TeacherInfoDao extends JpaRepository<TeacherInfo, Long>, TeacherInfoDaoCustom {

    /**
     * 根据id获取教师信息
     * @param teacherId 教师ID
     * @return
     */
    @Query(value = "select t.* from teacher_info t where t.teacher_id = ?1 ", nativeQuery = true)
    TeacherInfo getById(Long teacherId);
}
