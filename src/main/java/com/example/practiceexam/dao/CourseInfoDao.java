package com.example.practiceexam.dao;

import com.example.practiceexam.model.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 课程信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:55
 **/
public interface CourseInfoDao extends JpaRepository<CourseInfo, Long> {

    /**
     * 根据id获取课程
     * @param courseId 课程ID
     * @return
     */
    @Query(value = "select c.* from course_info c where c.course_id = ?1 ", nativeQuery = true)
    CourseInfo getById(Long courseId);

    /**
     * 根据id删除课程
     * @param courseId 课程ID
     * @return
     */
    @Modifying
    @Query(value = "delete from course_info where course_id = ?1 ", nativeQuery = true)
    int delById(Long courseId);

    /**
     * 获取所有课程
     * @return
     */
    @Query(value = "select * from course_info ", nativeQuery = true)
    List<CourseInfo> getList();

    /**
     * 获取所有课程
     * 模糊查询
     * @return
     */
    @Query(value = "select c.* from course_info c where c.course_name like %?1%", nativeQuery = true)
    List<CourseInfo> getList(String search);
}
