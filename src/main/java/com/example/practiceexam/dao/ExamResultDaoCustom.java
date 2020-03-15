package com.example.practiceexam.dao;

import com.example.practiceexam.dto.ExamResultDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/15  16:47
 **/
@Repository
public interface ExamResultDaoCustom {

    /**
     * 根据试卷ID和学生ID获取学生答题结果
     * @param paperId
     * @param studentId
     * @return
     */
    List<ExamResultDto> getQuesListByPaperIdAndStudent(Long paperId, Long studentId);
}
