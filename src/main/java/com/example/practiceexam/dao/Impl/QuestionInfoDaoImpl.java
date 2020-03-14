package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.QuestionInfoDaoCustom;
import com.example.practiceexam.dto.GenerateQuesDto;
import com.example.practiceexam.dto.QuesDto;
import com.example.practiceexam.param.GenerateSearchQuesParam;
import com.example.practiceexam.param.SearchQuesParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/12  10:59
 **/
@Repository
public class QuestionInfoDaoImpl implements QuestionInfoDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<QuesDto> getListByPage(SearchQuesParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select q.question_id questionId, q.question_code questionCode, q.course_id courseId, ci.course_name courseName, ");
        sqlSb.append(" q.question_type questionType, q.question_status questionStatus, q.question_difficulty questionDifficulty, ");
        sqlSb.append(" q.question_title questionTitle, q.question_answer questionAnswer, q.question_analyze questionAnalyze, ");
        sqlSb.append(" q.question_choice_a questionChoiceA, q.question_choice_b questionChoiceB, q.question_choice_c questionChoiceC, q.question_choice_d questionChoiceD, ");
        sqlSb.append(" q.create_user_id createUserId, cu.nick_name createUserName, q.create_time createTime, ");
        sqlSb.append(" q.review_user_id reviewUserId, ru.nick_name reviewUserName, q.review_time reviewTime ");
        sqlSb.append(" from question_info q ");
        sqlSb.append(" left join course_info ci on q.course_id = ci.course_id ");
        sqlSb.append(" left join user_info cu on q.create_user_id = cu.user_id ");
        sqlSb.append(" left join user_info ru on q.review_user_id = ru.user_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (q.question_code LIKE :search or q.question_title LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getCourseId() != null) {
            sqlSb.append(" AND q.course_id = :courseId ");
            paramMap.put("courseId", param.getCourseId());
        }
        if (param.getQuestionType() != null) {
            sqlSb.append(" AND q.question_type = :questionType ");
            paramMap.put("questionType", param.getQuestionType());
        }
        if (param.getQuestionStatus() != null) {
            sqlSb.append(" AND q.question_status = :questionStatus ");
            paramMap.put("questionStatus", param.getQuestionStatus());
        }
        if (param.getQuestionDifficulty() != null) {
            sqlSb.append(" AND q.question_difficulty = :questionDifficulty ");
            paramMap.put("questionDifficulty", param.getQuestionDifficulty());
        }
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("questionId", StandardBasicTypes.LONG)
                .addScalar("questionCode", StandardBasicTypes.STRING)
                .addScalar("courseId", StandardBasicTypes.LONG)
                .addScalar("courseName", StandardBasicTypes.STRING)
                .addScalar("questionType", StandardBasicTypes.INTEGER)
                .addScalar("questionStatus", StandardBasicTypes.INTEGER)
                .addScalar("questionDifficulty", StandardBasicTypes.INTEGER)
                .addScalar("questionTitle", StandardBasicTypes.STRING)
                .addScalar("questionAnswer", StandardBasicTypes.STRING)
                .addScalar("questionAnalyze", StandardBasicTypes.STRING)
                .addScalar("questionChoiceA", StandardBasicTypes.STRING)
                .addScalar("questionChoiceB", StandardBasicTypes.STRING)
                .addScalar("questionChoiceC", StandardBasicTypes.STRING)
                .addScalar("questionChoiceD", StandardBasicTypes.STRING)
                .addScalar("reviewUserId", StandardBasicTypes.LONG)
                .addScalar("reviewUserName", StandardBasicTypes.STRING)
                .addScalar("reviewTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("createUserId", StandardBasicTypes.LONG)
                .addScalar("createUserName", StandardBasicTypes.STRING)
                .addScalar("createTime", StandardBasicTypes.TIMESTAMP);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        query.setFirstResult(param.getOffset());
        query.setMaxResults(param.getLimit());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(QuesDto.class));
        return query.list();
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer getCountByPage(SearchQuesParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT COUNT(*) count ");
        sqlSb.append(" from question_info q ");
        sqlSb.append(" left join course_info ci on q.course_id = ci.course_id ");
        sqlSb.append(" left join user_info cu on q.create_user_id = cu.user_id ");
        sqlSb.append(" left join user_info ru on q.review_user_id = ru.user_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (q.question_code LIKE :search or q.question_title LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getCourseId() != null) {
            sqlSb.append(" AND q.course_id = :courseId ");
            paramMap.put("courseId", param.getCourseId());
        }
        if (param.getQuestionType() != null) {
            sqlSb.append(" AND q.question_type = :questionType ");
            paramMap.put("questionType", param.getQuestionType());
        }
        if (param.getQuestionStatus() != null) {
            sqlSb.append(" AND q.question_status = :questionStatus ");
            paramMap.put("questionStatus", param.getQuestionStatus());
        }
        if (param.getQuestionDifficulty() != null) {
            sqlSb.append(" AND q.question_difficulty = :questionDifficulty ");
            paramMap.put("questionDifficulty", param.getQuestionDifficulty());
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("count", StandardBasicTypes.LONG);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        Long count = (Long) query.uniqueResult();
        if (count != null) {
            return count.intValue();
        }
        return 0;
    }

    /**
     * 组卷关系获取试题
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<GenerateQuesDto> generateGetListByPage(GenerateSearchQuesParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select q.question_id questionId, q.question_code questionCode, ");
        sqlSb.append(" q.question_type questionType, q.question_status questionStatus, q.question_difficulty questionDifficulty, ");
        sqlSb.append(" q.question_title questionTitle, q.question_answer questionAnswer, q.question_analyze questionAnalyze, q.create_time createTime, ");
        sqlSb.append(" q.question_choice_a questionChoiceA, q.question_choice_b questionChoiceB, q.question_choice_c questionChoiceC, q.question_choice_d questionChoiceD ");
        sqlSb.append(" from question_info q WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (q.question_code LIKE :search or q.question_title LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getCourseId() != null) {
            sqlSb.append(" AND q.course_id = :courseId ");
            paramMap.put("courseId", param.getCourseId());
        }
        if (param.getQuestionSource() != null && param.getQuestionSource().equals(2) && param.getCreateUserId() != null) {
            sqlSb.append(" AND q.create_user_id = :createUserId ");
            paramMap.put("createUserId", param.getCreateUserId());
        }
        if (param.getQuestionType() != null) {
            sqlSb.append(" AND q.question_type = :questionType ");
            paramMap.put("questionType", param.getQuestionType());
        }
        if (param.getQuestionStatus() != null) {
            sqlSb.append(" AND q.question_status = :questionStatus ");
            paramMap.put("questionStatus", param.getQuestionStatus());
        }
        if (param.getQuestionDifficulty() != null) {
            sqlSb.append(" AND q.question_difficulty = :questionDifficulty ");
            paramMap.put("questionDifficulty", param.getQuestionDifficulty());
        }
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("questionId", StandardBasicTypes.LONG)
                .addScalar("questionCode", StandardBasicTypes.STRING)
                .addScalar("questionType", StandardBasicTypes.INTEGER)
                .addScalar("questionStatus", StandardBasicTypes.INTEGER)
                .addScalar("questionDifficulty", StandardBasicTypes.INTEGER)
                .addScalar("questionTitle", StandardBasicTypes.STRING)
                .addScalar("questionAnswer", StandardBasicTypes.STRING)
                .addScalar("questionAnalyze", StandardBasicTypes.STRING)
                .addScalar("questionChoiceA", StandardBasicTypes.STRING)
                .addScalar("questionChoiceB", StandardBasicTypes.STRING)
                .addScalar("questionChoiceC", StandardBasicTypes.STRING)
                .addScalar("questionChoiceD", StandardBasicTypes.STRING);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        query.setFirstResult(param.getOffset());
        query.setMaxResults(param.getLimit());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(GenerateQuesDto.class));
        return query.list();
    }

    /**
     * 组卷关系获取试题
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer generateGetCountByPage(GenerateSearchQuesParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT COUNT(*) count ");
        sqlSb.append(" from question_info q WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND (q.question_code LIKE :search or q.question_title LIKE :search) ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getQuestionSource() != null && param.getQuestionSource().equals(2) && param.getCreateUserId() != null) {
            sqlSb.append(" AND q.create_user_id = :createUserId ");
            paramMap.put("createUserId", param.getCreateUserId());
        }
        if (param.getQuestionType() != null) {
            sqlSb.append(" AND q.question_type = :questionType ");
            paramMap.put("questionType", param.getQuestionType());
        }
        if (param.getQuestionStatus() != null) {
            sqlSb.append(" AND q.question_status = :questionStatus ");
            paramMap.put("questionStatus", param.getQuestionStatus());
        }
        if (param.getQuestionDifficulty() != null) {
            sqlSb.append(" AND q.question_difficulty = :questionDifficulty ");
            paramMap.put("questionDifficulty", param.getQuestionDifficulty());
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("count", StandardBasicTypes.LONG);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        Long count = (Long) query.uniqueResult();
        if (count != null) {
            return count.intValue();
        }
        return 0;
    }
}
