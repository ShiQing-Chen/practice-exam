package com.example.practiceexam.dao;

import com.example.practiceexam.model.QuestionReviewLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ShiQing_Chen  2020/3/12  23:05
 **/
public interface QuestionReviewLogDao extends JpaRepository<QuestionReviewLog, Long> {
}
