package com.example.practiceexam.dao;

import com.example.practiceexam.model.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 学生信息dao层
 * @author ShiQing_Chen  2020/3/5  17:53
 **/
public interface StudentInfoDao extends JpaRepository<StudentInfo, Long>, StudentInfoDaoCustom {
    /**
     * 根据id获取学生
     * @param studentId 学生ID
     * @return
     */
    @Query(value = "select s.* from student_info s where s.student_id = ?1 ", nativeQuery = true)
    StudentInfo getById(Long studentId);

    /**
     * 根据id删除学生
     * @param studentId 学生ID
     * @return
     */
    @Modifying
    @Query(value = "delete from student_info where student_id = ?1 ", nativeQuery = true)
    int delById(Long studentId);

    /**
     * 校验添加信息时
     * 学号是否已经存在
     * @param studentNumber
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM student_info s WHERE s.student_number = ?1", nativeQuery = true)
    int checkStudentNumber(String studentNumber);

    /**
     * 校验修改信息时
     * 学号是否已经存在
     * @param studentId
     * @param studentNumber
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM student_info s WHERE s.student_id <> ?1 AND s.student_number = ?2", nativeQuery = true)
    int checkStudentNumber(Long studentId, String studentNumber);
}
