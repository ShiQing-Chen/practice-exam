package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.ExamResultDaoCustom;
import com.example.practiceexam.dto.ExamResultDto;
import com.google.common.collect.Lists;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author ShiQing_Chen  2020/3/15  16:49
 **/
@Repository
public class ExamResultDaoImpl implements ExamResultDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 根据试卷ID和学生ID获取学生答题结果
     * @param paperId
     * @param studentId
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<ExamResultDto> getQuesListByPaperIdAndStudent(Long paperId, Long studentId) {
        if (paperId == null || studentId == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select er.result_id resultId, pg.paper_id paperId, er.student_id studentId, q.question_id questionId, ");
        sqlSb.append(" q.question_type questionType, q.question_difficulty questionDifficulty, pg.question_score questionScore, ");
        sqlSb.append(" q.question_title questionTitle, q.question_answer questionAnswer, q.question_analyze questionAnalyze, ");
        sqlSb.append(" q.question_choice_a questionChoiceA, q.question_choice_b questionChoiceB, q.question_choice_c questionChoiceC, q.question_choice_d questionChoiceD, ");
        sqlSb.append(" er.result_answer resultAnswer, er.result_status resultStatus, er.result_opinion resultOpinion, er.result_score resultScore ");
        sqlSb.append(" from paper_generate pg ");
        sqlSb.append(" left join question_info q on pg.question_id = q.question_id ");
        sqlSb.append(" left join exam_result er on pg.question_id = er.question_id ");
        sqlSb.append(" where pg.paper_id=:paperId and er.paper_id=:paperId and er.student_id=:studentId order by pg.order_number ");

        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("resultId", StandardBasicTypes.LONG)
                .addScalar("paperId", StandardBasicTypes.LONG)
                .addScalar("studentId", StandardBasicTypes.LONG)
                .addScalar("questionId", StandardBasicTypes.LONG)
                .addScalar("questionType", StandardBasicTypes.INTEGER)
                .addScalar("questionDifficulty", StandardBasicTypes.INTEGER)
                .addScalar("questionTitle", StandardBasicTypes.STRING)
                .addScalar("questionAnswer", StandardBasicTypes.STRING)
                .addScalar("questionAnalyze", StandardBasicTypes.STRING)
                .addScalar("questionChoiceA", StandardBasicTypes.STRING)
                .addScalar("questionChoiceB", StandardBasicTypes.STRING)
                .addScalar("questionChoiceC", StandardBasicTypes.STRING)
                .addScalar("questionChoiceD", StandardBasicTypes.STRING)
                .addScalar("questionScore", StandardBasicTypes.BIG_DECIMAL)
                .addScalar("resultAnswer", StandardBasicTypes.STRING)
                .addScalar("resultStatus", StandardBasicTypes.INTEGER)
                .addScalar("resultOpinion", StandardBasicTypes.STRING)
                .addScalar("resultScore", StandardBasicTypes.BIG_DECIMAL);
        query.setParameter("paperId", paperId);
        query.setParameter("studentId", studentId);
        query.setFirstResult(0);
        query.setMaxResults(1000);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ExamResultDto.class));
        return query.list();
    }
}
