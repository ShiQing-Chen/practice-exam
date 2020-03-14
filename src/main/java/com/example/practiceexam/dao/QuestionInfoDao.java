package com.example.practiceexam.dao;

import com.example.practiceexam.model.QuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    /**
     * 随机获取到某课程下
     * 待审核的试题
     * @param courseId 课程ID
     * @return
     */
    @Query(value = "select * from question_info where course_id = ?1 and question_status =2 order by rand() limit 1", nativeQuery = true)
    QuestionInfo getReadyReviewByCourseId(Long courseId);

    /**
     * 根据试卷ID获取试题
     * @param paperId
     * @return
     */
    @Query(value = "select q.* from paper_generate pg " +
            "left join question_info q on pg.question_id = q.question_id " +
            "where pg.paper_id=?1 order by pg.order_number", nativeQuery = true)
    List<QuestionInfo> getQuesListByPaperId(Long paperId);

}
