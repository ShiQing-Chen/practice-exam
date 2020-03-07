package com.example.practiceexam.dao;

import com.example.practiceexam.model.ClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 班级信息Dao层
 * @author ShiQing_Chen  2020/3/5  17:55
 **/
public interface ClassInfoDao extends JpaRepository<ClassInfo, Long>, ClassInfoDaoCustom {
    /**
     * 根据id获取班级
     * @param classId 班级ID
     * @return
     */
    @Query(value = "select c.* from class_info c where c.class_id = ?1 ", nativeQuery = true)
    ClassInfo getById(Long classId);

    /**
     * 根据id删除班级
     * @param classId 班级ID
     * @return
     */
    @Modifying
    @Query(value = "delete from class_info where class_id = ?1 ", nativeQuery = true)
    int delById(Long classId);

    /**
     * 根据id删除班级
     * @param classId 班级ID
     * @return
     */
    @Modifying
    @Query(value = "update class_info set teacher_id = ?2 where class_id = ?1 ", nativeQuery = true)
    int setClassTeacher(Long classId, Long teacherId);

    /**
     * 获取所有班级名称
     * @return
     */
    @Query(value = "select distinct c.class_name from class_info c ", nativeQuery = true)
    List<String> getListClassName();

    /**
     * 获取所有专业名称
     * @return
     */
    @Query(value = "select distinct c.major_name from class_info c ", nativeQuery = true)
    List<String> getListMajorName();
}
