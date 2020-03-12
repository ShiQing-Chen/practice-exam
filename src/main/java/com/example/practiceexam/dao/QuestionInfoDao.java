package com.example.practiceexam.dao;

import com.example.practiceexam.model.QuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 试题Dao层
 * @author ShiQing_Chen  2020/3/12  01:59
 **/
public interface QuestionInfoDao extends JpaRepository<QuestionInfo, Long>, QuestionInfoDaoCustom {
    /**
     * 根据id获取试题
     * @param questionId 试题ID
     * @return
     */
    @Query(value = "select q.* from question_info q where q.question_id = ?1 ", nativeQuery = true)
    QuestionInfo getById(Long questionId);

    /**
     * 根据id删除试题
     * @param questionId 试题ID
     * @return
     */
    @Modifying
    @Query(value = "delete from question_info where question_id = ?1 ", nativeQuery = true)
    int delById(Long questionId);
}
