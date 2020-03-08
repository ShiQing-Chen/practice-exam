package com.example.practiceexam.dao;

import com.example.practiceexam.model.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    /**
     * 根据id删除教师
     * @param teacherId 教师ID
     * @return
     */
    @Modifying
    @Query(value = "delete from teacher_info where teacher_id = ?1 ", nativeQuery = true)
    int delById(Long teacherId);

    /**
     * 校验添加信息时
     * 工号是否已经存在
     * @param teacherNumber
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM teacher_info t WHERE t.teacher_number = ?1", nativeQuery = true)
    int checkTeacherNumber(String teacherNumber);

    /**
     * 校验修改信息时
     * 工号是否已经存在
     * @param teacherId
     * @param teacherNumber
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM teacher_info t WHERE t.teacher_id <> ?1 AND t.teacher_number = ?2", nativeQuery = true)
    int checkTeacherNumber(Long teacherId, String teacherNumber);
}
