package com.example.practiceexam.dao;

import com.example.practiceexam.model.PaperGenerate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
 * @author ShiQing_Chen  2020/3/14  12:21
 **/
public interface PaperGenerateDao extends JpaRepository<PaperGenerate, Long> {

    /**
     * 根据试卷ID获取选择题分数
     * @param paperId 试卷ID
     * @return
     */
    @Query(value = "select distinct pg.question_score from paper_generate pg " +
            "left join question_info q on pg.question_id = q.question_id " +
            "where pg.paper_id = ?1 and q.question_type = 1 and pg.question_score is not null and pg.question_score <>0 " +
            "group by pg.question_score", nativeQuery = true)
    BigDecimal getChoiceScoreByPaperId(Long paperId);

    /**
     * 根据试卷ID获取非选择题分数
     * @param paperId 试卷ID
     * @return
     */
    @Query(value = "select distinct pg.question_score from paper_generate pg " +
            "left join question_info q on pg.question_id = q.question_id " +
            "where pg.paper_id = ?1 and q.question_type = 2 and pg.question_score is not null and pg.question_score <>0 " +
            "group by pg.question_score", nativeQuery = true)
    BigDecimal getSubjectiveScoreByPaperId(Long paperId);
}
