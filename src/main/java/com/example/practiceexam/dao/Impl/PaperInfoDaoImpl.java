package com.example.practiceexam.dao.Impl;

import com.example.practiceexam.dao.PaperInfoDaoCustom;
import com.example.practiceexam.dto.PaperInfoDto;
import com.example.practiceexam.param.SearchPaperParam;
import com.example.practiceexam.param.StudentSearchPaperParam;
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
 * @author ShiQing_Chen  2020/3/14  02:37
 **/
@Repository
public class PaperInfoDaoImpl implements PaperInfoDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<PaperInfoDto> getListByPage(SearchPaperParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select p.paper_id paperId, p.paper_name paperName, p.do_time doTime, p.paper_type paperType, ");
        sqlSb.append(" p.paper_status paperStatus, p.course_id courseId, ci.course_name courseName, ");
        sqlSb.append(" p.start_time startTime, p.end_time endTime, ");
        sqlSb.append(" p.create_user_id createUserId, cu.nick_name createUserName, p.create_time createTime, ");
        sqlSb.append(" p.publish_user_id publishUserId, pu.nick_name publishUserName, p.publish_time publishTime ");
        sqlSb.append(" from paper_info p ");
        sqlSb.append(" left join course_info ci on p.course_id = ci.course_id ");
        sqlSb.append(" left join user_info cu on p.create_user_id = cu.user_id ");
        sqlSb.append(" left join user_info pu on p.publish_user_id = pu.user_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND p.paper_name LIKE :search ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getCourseId() != null) {
            sqlSb.append(" AND p.course_id = :courseId ");
            paramMap.put("courseId", param.getCourseId());
        }
        if (param.getPaperType() != null) {
            sqlSb.append(" AND p.paper_type = :paperType ");
            paramMap.put("paperType", param.getPaperType());
        }
        if (param.getPaperStatus() != null) {
            sqlSb.append(" AND p.paper_status = :paperStatus ");
            paramMap.put("paperStatus", param.getPaperStatus());
        }
        if (param.getCreateUserId() != null) {
            sqlSb.append(" AND p.create_user_id = :createUserId ");
            paramMap.put("createUserId", param.getCreateUserId());
        }
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("paperId", StandardBasicTypes.LONG)
                .addScalar("courseId", StandardBasicTypes.LONG)
                .addScalar("courseName", StandardBasicTypes.STRING)
                .addScalar("paperName", StandardBasicTypes.STRING)
                .addScalar("doTime", StandardBasicTypes.INTEGER)
                .addScalar("paperType", StandardBasicTypes.INTEGER)
                .addScalar("paperStatus", StandardBasicTypes.INTEGER)
                .addScalar("startTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("endTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("publishUserId", StandardBasicTypes.LONG)
                .addScalar("publishUserName", StandardBasicTypes.STRING)
                .addScalar("publishTime", StandardBasicTypes.TIMESTAMP)
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
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PaperInfoDto.class));
        return query.list();
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer getCountByPage(SearchPaperParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT COUNT(*) count ");
        sqlSb.append(" from paper_info p ");
        sqlSb.append(" left join user_info cu on p.create_user_id = cu.user_id ");
        sqlSb.append(" left join user_info pu on p.publish_user_id = pu.user_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND p.paper_name LIKE :search ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getPaperType() != null) {
            sqlSb.append(" AND p.paper_type = :paperType ");
            paramMap.put("paperType", param.getPaperType());
        }
        if (param.getPaperStatus() != null) {
            sqlSb.append(" AND p.paper_status = :paperStatus ");
            paramMap.put("paperStatus", param.getPaperStatus());
        }
        if (param.getCreateUserId() != null) {
            sqlSb.append(" AND p.create_user_id = :createUserId ");
            paramMap.put("createUserId", param.getCreateUserId());
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
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public List<PaperInfoDto> studentGetListByPage(StudentSearchPaperParam param) {
        if (param == null) {
            return Lists.newArrayList();
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" select p.paper_id paperId, p.paper_name paperName, p.do_time doTime, p.paper_type paperType, ");
        sqlSb.append(" p.paper_status paperStatus, p.create_time createTime, p.publish_time publishTime, ");
        sqlSb.append(" p.start_time startTime, p.end_time endTime, count(er.result_id) resultNumber ");
        sqlSb.append(" from paper_info p ");
        sqlSb.append(" left join paper_class pc on pc.paper_id = p.paper_id ");
        sqlSb.append(" left join exam_result er on er.paper_id = p.paper_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND p.paper_name LIKE :search ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getPaperType() != null) {
            sqlSb.append(" AND p.paper_type = :paperType ");
            paramMap.put("paperType", param.getPaperType());
        }
        if (param.getPaperStatus() != null) {
            sqlSb.append(" AND p.paper_status = :paperStatus ");
            paramMap.put("paperStatus", param.getPaperStatus());
        }
        if (param.getClassId() != null) {
            sqlSb.append(" AND pc.class_id = :classId ");
            paramMap.put("classId", param.getClassId());
        }
        sqlSb.append(" group by p.paper_id ");
        if ("desc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" desc");
        } else if ("asc".equals(param.getOrder()) && StringUtils.isNotBlank(param.getSort())) {
            sqlSb.append(" order by ").append(param.getSort()).append(" asc");
        }
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createSQLQuery(sqlSb.toString());
        query.addScalar("paperId", StandardBasicTypes.LONG)
                .addScalar("paperName", StandardBasicTypes.STRING)
                .addScalar("doTime", StandardBasicTypes.INTEGER)
                .addScalar("paperType", StandardBasicTypes.INTEGER)
                .addScalar("paperStatus", StandardBasicTypes.INTEGER)
                .addScalar("resultNumber", StandardBasicTypes.INTEGER)
                .addScalar("startTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("endTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("publishTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("createTime", StandardBasicTypes.TIMESTAMP);
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> m : paramMap.entrySet()) {
                query.setParameter(m.getKey(), m.getValue());
            }
        }
        query.setFirstResult(param.getOffset());
        query.setMaxResults(param.getLimit());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PaperInfoDto.class));
        return query.list();
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public Integer studentGetCountByPage(StudentSearchPaperParam param) {
        if (param == null) {
            return 0;
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(" SELECT COUNT(*) count ");
        sqlSb.append(" from paper_info p ");
        sqlSb.append(" left join paper_class pc on pc.paper_id = p.paper_id WHERE 1=1 ");

        Map<String, Object> paramMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(param.getSearch())) {
            sqlSb.append(" AND p.paper_name LIKE :search ");
            paramMap.put("search", "%" + param.getSearch() + "%");
        }
        if (param.getPaperType() != null) {
            sqlSb.append(" AND p.paper_type = :paperType ");
            paramMap.put("paperType", param.getPaperType());
        }
        if (param.getPaperStatus() != null) {
            sqlSb.append(" AND p.paper_status = :paperStatus ");
            paramMap.put("paperStatus", param.getPaperStatus());
        }
        if (param.getClassId() != null) {
            sqlSb.append(" AND pc.class_id = :classId ");
            paramMap.put("classId", param.getClassId());
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
