package com.example.practiceexam.dao;

import com.example.practiceexam.model.QuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 试题Dao层
 * @author ShiQing_Chen  2020/3/12  01:59
 **/
public interface QuestionInfoDao  extends JpaRepository<QuestionInfo, Long> {
}
