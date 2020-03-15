package com.example.practiceexam.dao;

import com.example.practiceexam.model.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/15  15:22
 **/
public interface ExamResultDao extends JpaRepository<ExamResult, Long>, ExamResultDaoCustom {
    /**
     * 根据id获取
     * @param resultId 结果ID
     * @return
     */
    @Query(value = "select er.* from exam_result er where er.result_id = ?1 ", nativeQuery = true)
    ExamResult getById(Long resultId);

    /**
     * 根据试卷ID和学生ID获取答题结果数量
     * @param paperId
     * @param studentId
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM exam_result er WHERE er.paper_id = ?1 and er.student_id = ?2", nativeQuery = true)
    int getCountByPaperIdAndStudentId(Long paperId, Long studentId);

    /**
     * 根据试卷ID和学生ID获取学生总分
     * @param paperId
     * @param studentId
     * @return
     */
    @Query(value = "SELECT SUM(er.result_score) FROM exam_result er WHERE er.paper_id = ?1 and er.student_id = ?2", nativeQuery = true)
    BigDecimal getStudentScoreByPaperIdAndStudentId(Long paperId, Long studentId);

     /**
     * 批改
     * 根据试卷ID和试题ID随机获取未批改的结果
     * @param paperId
     * @return
     */
    @Query(value = "select * from exam_result where paper_id = ?1 and result_status =1 and question_id = ?2 order by rand() limit 1", nativeQuery = true)
    List<ExamResult> getResultByPaperIdAndQuesId(Long paperId, Long questionId);
}
